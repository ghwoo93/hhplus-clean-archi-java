package io.hhplus.clean.architect.lecture.mapper;

import io.hhplus.clean.architect.lecture.aggregate.domain.LectureDomain;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.aggregate.entity.Lecture;

public class LectureMapper {

    public static LectureDTO toDTO(LectureDomain domain) {
        return new LectureDTO(domain.getLectureId(), domain.getTitle());
    }

    public static LectureDomain toDomain(Lecture entity) {
        System.err.println("toDomain" + entity.);
        return new LectureDomain(entity.lectureId(), entity.title());
    }

    public static Lecture toEntity(LectureDomain domain) {
        return new Lecture(domain.getLectureId(), domain.getTitle());
    }
}