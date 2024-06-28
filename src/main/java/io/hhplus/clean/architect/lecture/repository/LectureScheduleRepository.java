package io.hhplus.clean.architect.lecture.repository;

import java.util.List;
import java.util.Optional;

import io.hhplus.clean.architect.lecture.aggregate.entity.LectureSchedule;

public interface LectureScheduleRepository {
    List<LectureSchedule> findAll();
    List<LectureSchedule> findByLectureId(Long lectureId);
    Optional<LectureSchedule> findById(Long scheduleId);
    void save(LectureSchedule lectureSchedule);
}