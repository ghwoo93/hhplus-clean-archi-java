package io.hhplus.clean.architect.lecture.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.hhplus.clean.architect.lecture.adapter.mapper.LectureMapper;
import io.hhplus.clean.architect.lecture.adapter.mapper.LectureRegistrationMapper;
import io.hhplus.clean.architect.lecture.adapter.mapper.LectureScheduleMapper;
import io.hhplus.clean.architect.lecture.adapter.repository.LectureRegistrationRepository;
import io.hhplus.clean.architect.lecture.adapter.repository.LectureRepository;
import io.hhplus.clean.architect.lecture.adapter.repository.LectureScheduleRepository;
import io.hhplus.clean.architect.lecture.domain.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.domain.dto.LectureRegistrationDTO;
import io.hhplus.clean.architect.lecture.domain.dto.LectureScheduleDTO;
import io.hhplus.clean.architect.lecture.domain.model.LectureSchedule;
import io.hhplus.clean.architect.lecture.domain.service.LectureDomainService;
import io.hhplus.clean.architect.lecture.exception.LectureBusinessException;
import io.hhplus.clean.architect.lecture.helper.FairLockHelper;
import jakarta.persistence.LockModeType;

@Service
public class LectureService {

    private final LectureRepository lectureRepository;
    private final LectureScheduleRepository lectureScheduleRepository;
    private final LectureRegistrationRepository lectureRegistrationRepository;
    private final FairLockHelper fairLockHelper;
    private final LectureDomainService lectureDomainService;

    @Autowired
    public LectureService(LectureRepository lectureRepository,
                          LectureScheduleRepository lectureScheduleRepository,
                          LectureRegistrationRepository lectureRegistrationRepository,
                          FairLockHelper fairLockHelper,
                          LectureDomainService lectureDomainService) {
        this.lectureRepository = lectureRepository;
        this.lectureScheduleRepository = lectureScheduleRepository;
        this.lectureRegistrationRepository = lectureRegistrationRepository;
        this.fairLockHelper = fairLockHelper;
        this.lectureDomainService = lectureDomainService;
    }

    public List<LectureDTO> getAllLectures() {
        return lectureRepository.findAll().stream()
                .map(LectureMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<LectureScheduleDTO> getLectureSchedules(Long lectureId) {
        return lectureScheduleRepository.findByLectureId(lectureId).stream()
                .map(LectureScheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean applyForLecture(Long userId, Long scheduleId) {
        try {
            // 우선적으로 동일 사용자가 동일 스케줄에 이미 신청했는지 확인
            boolean alreadyRegistered = lectureRegistrationRepository.existsByUserIdAndScheduleId(userId, scheduleId);
            if (alreadyRegistered) {
                throw new LectureBusinessException("User has already applied for this schedule");
            }

            // 비관적 락을 걸고 스케줄 정보를 가져옴
            Optional<LectureSchedule> scheduleOpt = lectureScheduleRepository.findByIdWithLock(scheduleId, LockModeType.PESSIMISTIC_WRITE);
            if (!scheduleOpt.isPresent()) {
                throw new LectureBusinessException("Invalid schedule ID");
            }

            LectureSchedule schedule = scheduleOpt.get();
            long currentRegistrations = lectureRegistrationRepository.countByScheduleId(scheduleId);
            if (currentRegistrations >= schedule.getCapacity()) {
                throw new LectureBusinessException("Lecture is full");
            }

            // 신청을 등록
            lectureDomainService.registerForLecture(userId, scheduleId);
            return true; // 신청 성공
        } catch (Exception e) {
            // 예외 처리
            throw new LectureBusinessException("Failed to apply for lecture: " + e.getMessage()); // 신청 실패
        }
    }

    public boolean checkRegistrationStatus(Long userId) {
        return lectureRegistrationRepository.existsByUserId(userId);
    }

    public List<LectureRegistrationDTO> getRegistrationHistory(Long scheduleId) {
        return lectureRegistrationRepository.findByScheduleId(scheduleId).stream()
                .map(LectureRegistrationMapper::toDTO)
                .collect(Collectors.toList());
    }
}
