package io.hhplus.clean.architect.lecture.adapter.mapper;

import io.hhplus.clean.architect.lecture.domain.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.domain.model.Lecture;

public class LectureMapper {

    public static LectureDTO toDTO(Lecture lecture) {
        return new LectureDTO(
                lecture.getLectureId(),
                lecture.getTitle()
        );
    }

    public static Lecture toEntity(LectureDTO dto) {
        return new Lecture(
                dto.getLectureId(),
                dto.getTitle()
        );
    }
}
