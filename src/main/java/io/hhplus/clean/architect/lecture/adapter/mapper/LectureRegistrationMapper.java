package io.hhplus.clean.architect.lecture.adapter.mapper;

import io.hhplus.clean.architect.lecture.domain.dto.LectureRegistrationDTO;
import io.hhplus.clean.architect.lecture.domain.model.LectureRegistration;

public class LectureRegistrationMapper {

    public static LectureRegistrationDTO toDTO(LectureRegistration registration) {
        return new LectureRegistrationDTO(
                registration.getRegistrationId(),
                registration.getUserId(),
                registration.getScheduleId(),
                registration.getRegistrationDate()
        );
    }

    public static LectureRegistration toEntity(LectureRegistrationDTO dto) {
        return new LectureRegistration(
                dto.getRegistrationId(), 
                dto.getUserId(),
                dto.getScheduleId(),
                dto.getRegistrationDate()
        );
    }
}
