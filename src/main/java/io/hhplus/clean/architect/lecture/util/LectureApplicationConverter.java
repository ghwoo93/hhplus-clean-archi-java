package io.hhplus.clean.architect.lecture.util;

import io.hhplus.clean.architect.lecture.aggregate.dto.LectureApplicationDTO;
import io.hhplus.clean.architect.lecture.aggregate.entity.LectureApplication;
import io.hhplus.clean.architect.lecture.aggregate.vo.LectureApplicationVO;

public class LectureApplicationConverter {

    public static LectureApplicationVO toVO(LectureApplication lectureApplication) {
        return new LectureApplicationVO(
                lectureApplication.applicationId(),
                UserConverter.toVO(lectureApplication.user()),
                LectureConverter.toVO(lectureApplication.lecture()),
                lectureApplication.applicationDate()
        );
    }

    public static LectureApplicationDTO toDTO(LectureApplication lectureApplication) {
        return new LectureApplicationDTO(
                lectureApplication.applicationId(),
                UserConverter.toDTO(lectureApplication.user()),
                LectureConverter.toDTO(lectureApplication.lecture()),
                lectureApplication.applicationDate()
        );
    }

    public static LectureApplication toEntity(LectureApplicationDTO lectureApplicationDTO) {
        return new LectureApplication(
                lectureApplicationDTO.getApplicationId(),
                UserConverter.toEntity(lectureApplicationDTO.getUser()),
                LectureConverter.toEntity(lectureApplicationDTO.getLecture()),
                lectureApplicationDTO.getApplicationDate()
        );
    }
}
