package io.hhplus.clean.architect.lecture.mapper;

import io.hhplus.clean.architect.lecture.aggregate.domain.LectureRegistrationDomain;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureRegistrationDTO;
import io.hhplus.clean.architect.lecture.aggregate.entity.LectureRegistration;

public class LectureRegistrationMapper {

    public static LectureRegistrationDTO toDTO(LectureRegistrationDomain domain) {
        return new LectureRegistrationDTO(domain.getRegistrationId(), domain.getUserId(), domain.getScheduleId(), domain.getRegistrationDate());
    }

    public static LectureRegistrationDomain toDomain(LectureRegistration entity) {
        return new LectureRegistrationDomain(entity.getRegistrationId(), entity.getUserId(), entity.getScheduleId(), entity.getRegistrationDate());
    }

    public static LectureRegistration toEntity(LectureRegistrationDomain domain) {
        return new LectureRegistration(domain.getRegistrationId(), domain.getUserId(), domain.getScheduleId(), domain.getRegistrationDate());
    }
}
