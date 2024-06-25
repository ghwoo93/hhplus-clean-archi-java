package io.hhplus.clean.architect.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.hhplus.clean.architect.lecture.aggregate.entity.Lecture;
import io.hhplus.clean.architect.lecture.aggregate.entity.LectureApplication;
import io.hhplus.clean.architect.lecture.aggregate.entity.User;

public interface LectureApplicationRepository extends JpaRepository<LectureApplication, Long> {
    boolean existsByUserAndLecture(User user, Lecture lecture);
    long countByLecture(Lecture lecture);
}
