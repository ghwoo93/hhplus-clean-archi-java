package io.hhplus.clean.architect.lecture.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import io.hhplus.clean.architect.lecture.aggregate.entity.Lecture;

public interface LectureRepository extends JpaRepository<Lecture, Long> {
}