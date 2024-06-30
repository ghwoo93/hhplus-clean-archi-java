package io.hhplus.clean.architect.lecture.adapter.repository;

import java.util.List;

import io.hhplus.clean.architect.lecture.domain.model.LectureRegistration;

public interface LectureRegistrationRepository {
    boolean existsByUserIdAndScheduleId(Long userId, Long scheduleId);
    boolean existsByUserId(Long userId);
    long countByScheduleId(Long scheduleId);
    List<LectureRegistration> findByScheduleId(Long scheduleId);
    void save(LectureRegistration lectureRegistration);
}