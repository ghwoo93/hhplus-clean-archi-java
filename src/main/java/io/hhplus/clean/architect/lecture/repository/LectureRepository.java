package io.hhplus.clean.architect.lecture.repository;

import java.util.List;

import io.hhplus.clean.architect.lecture.aggregate.entity.Lecture;

public interface LectureRepository {
    List<Lecture> findAll();
}