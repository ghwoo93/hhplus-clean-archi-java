package io.hhplus.clean.architect.lecture.adapter.repository;

import java.util.List;
import java.util.Optional;

import io.hhplus.clean.architect.lecture.domain.model.LectureSchedule;
import jakarta.persistence.LockModeType;

public interface LectureScheduleRepository {
    List<LectureSchedule> findAll();
    List<LectureSchedule> findByLectureId(Long lectureId);
    Optional<LectureSchedule> findByIdWithLock(Long scheduleId, LockModeType lockModeType);
    void save(LectureSchedule lectureSchedule);
}