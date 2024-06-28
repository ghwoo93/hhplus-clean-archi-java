package io.hhplus.clean.architect.lecture.repository;

import java.util.List;

import io.hhplus.clean.architect.lecture.aggregate.entity.LectureRegistration;

public interface LectureRegistrationRepository {
    boolean existsByUserIdAndScheduleId(Long userId, Long scheduleId);
    boolean existsByUserId(Long userId);
    long countByScheduleId(Long scheduleId);
    List<LectureRegistration> findByScheduleId(Long scheduleId);
    void save(LectureRegistration lectureRegistration);
}