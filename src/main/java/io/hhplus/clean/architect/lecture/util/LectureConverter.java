package io.hhplus.clean.architect.lecture.util;

import io.hhplus.clean.architect.lecture.aggregate.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.aggregate.entity.Lecture;
import io.hhplus.clean.architect.lecture.aggregate.vo.LectureVO;

public class LectureConverter {

    public static LectureVO toVO(Lecture lecture) {
        return new LectureVO(lecture.lectureId(), lecture.title(), lecture.date(), lecture.capacity());
    }

    public static LectureDTO toDTO(Lecture lecture) {
        return new LectureDTO(lecture.lectureId(), lecture.title(), lecture.date(), lecture.capacity());
    }

    public static Lecture toEntity(LectureDTO lectureDTO) {
        return new Lecture(lectureDTO.getLectureId(), lectureDTO.getTitle(), lectureDTO.getDate(), lectureDTO.getCapacity());
    }
}
