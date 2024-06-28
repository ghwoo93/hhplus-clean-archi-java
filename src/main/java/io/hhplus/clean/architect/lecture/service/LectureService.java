package io.hhplus.clean.architect.lecture.service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.hhplus.clean.architect.lecture.aggregate.domain.LectureRegistrationDomain;
import io.hhplus.clean.architect.lecture.aggregate.domain.LectureScheduleDomain;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureRegistrationDTO;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureScheduleDTO;
import io.hhplus.clean.architect.lecture.aggregate.entity.LectureSchedule;
import io.hhplus.clean.architect.lecture.exception.LectureBusinessException;
import io.hhplus.clean.architect.lecture.helper.FairLockHelper;
import io.hhplus.clean.architect.lecture.mapper.LectureMapper;
import io.hhplus.clean.architect.lecture.mapper.LectureRegistrationMapper;
import io.hhplus.clean.architect.lecture.mapper.LectureScheduleMapper;
import io.hhplus.clean.architect.lecture.repository.LectureRegistrationRepositoryImpl;
import io.hhplus.clean.architect.lecture.repository.LectureRepositoryImpl;
import io.hhplus.clean.architect.lecture.repository.LectureScheduleRepositoryImpl;

@Service
public class LectureService {

    private final LectureRepositoryImpl lectureRepository;
    private final LectureScheduleRepositoryImpl lectureScheduleRepository;
    private final LectureRegistrationRepositoryImpl lectureRegistrationRepository;
    private final FairLockHelper fairLockHelper;

    @Autowired
    public LectureService(LectureRepositoryImpl lectureRepository, LectureScheduleRepositoryImpl lectureScheduleRepository, LectureRegistrationRepositoryImpl lectureRegistrationRepository, FairLockHelper fairLockHelper) {
        this.lectureRepository = lectureRepository;
        this.lectureScheduleRepository = lectureScheduleRepository;
        this.lectureRegistrationRepository = lectureRegistrationRepository;
        this.fairLockHelper = fairLockHelper;
    }

    public List<LectureDTO> getAllLectures() {
        return lectureRepository.findAll().stream()
                .map(LectureMapper::toDomain)
                .map(LectureMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<LectureScheduleDTO> getLectureSchedules(Long lectureId) {
        return lectureScheduleRepository.findByLectureId(lectureId).stream()
                .map(LectureScheduleMapper::toDomain)
                .map(LectureScheduleMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public boolean applyForLecture (Long userId, Long scheduleId) throws Exception {
        return fairLockHelper.executeWithLock(() -> {
            Optional<LectureSchedule> scheduleOpt = lectureScheduleRepository.findById(scheduleId);
            if (!scheduleOpt.isPresent()) {
                throw new LectureBusinessException("Invalid schedule ID");
            }

            LectureScheduleDomain schedule = LectureScheduleMapper.toDomain(scheduleOpt.get());
            long currentRegistrations = lectureRegistrationRepository.countByScheduleId(scheduleId);
            if (currentRegistrations >= schedule.getCapacity()) {
                throw new LectureBusinessException("Lecture is full");
            }

            boolean alreadyRegistered = lectureRegistrationRepository.existsByUserIdAndScheduleId(userId, scheduleId);
            if (alreadyRegistered) {
                throw new LectureBusinessException("User has already applied for this schedule");
            }

            LectureRegistrationDomain registration = new LectureRegistrationDomain(null, userId, scheduleId, new Timestamp(System.currentTimeMillis()));
            lectureRegistrationRepository.save(LectureRegistrationMapper.toEntity(registration));

            return true; // Application successful
        });
    }

    public boolean checkRegistrationStatus(Long userId) {
        try {
            return lectureRegistrationRepository.existsByUserId(userId);
        } catch (Exception e) {
            throw new LectureBusinessException("Failed to check registration status", e);
        }
    }

    public List<LectureRegistrationDTO> getRegistrationHistory(Long scheduleId) {
        try {
            return lectureRegistrationRepository.findByScheduleId(scheduleId).stream()
                    .map(LectureRegistrationMapper::toDomain)
                    .map(LectureRegistrationMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LectureBusinessException("Failed to get registration history", e);
        }
    }

    public List<LectureScheduleDTO> getAllLectureSchedules() {
        try {
            return lectureScheduleRepository.findAll().stream()
                    .map(LectureScheduleMapper::toDomain)
                    .map(LectureScheduleMapper::toDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new LectureBusinessException("Failed to get all lecture schedules", e);
        }
    }
}
