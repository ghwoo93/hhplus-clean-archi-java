package io.hhplus.clean.architect.lecture.adapter.repository;

import java.util.List;

import io.hhplus.clean.architect.lecture.domain.model.Lecture;

public interface LectureRepository {
    List<Lecture> findAll();
}