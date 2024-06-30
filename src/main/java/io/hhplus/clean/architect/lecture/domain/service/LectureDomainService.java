package io.hhplus.clean.architect.lecture.domain.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hhplus.clean.architect.lecture.adapter.repository.LectureRegistrationRepository;
import io.hhplus.clean.architect.lecture.domain.model.LectureRegistration;

@Service
public class LectureDomainService {

    private final LectureRegistrationRepository lectureRegistrationRepository;

    @Autowired
    public LectureDomainService(LectureRegistrationRepository lectureRegistrationRepository) {
        this.lectureRegistrationRepository = lectureRegistrationRepository;
    }

    public void registerForLecture(Long userId, Long scheduleId) {
        LectureRegistration registration = new LectureRegistration(
                null, 
                userId,
                scheduleId,
                new Timestamp(System.currentTimeMillis())
        );
        lectureRegistrationRepository.save(registration);
    }
}
