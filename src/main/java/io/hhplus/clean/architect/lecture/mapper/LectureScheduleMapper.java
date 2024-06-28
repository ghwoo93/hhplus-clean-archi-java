package io.hhplus.clean.architect.lecture.mapper;

import io.hhplus.clean.architect.lecture.aggregate.domain.LectureScheduleDomain;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureScheduleDTO;
import io.hhplus.clean.architect.lecture.aggregate.entity.LectureSchedule;

public class LectureScheduleMapper {

    public static LectureScheduleDTO toDTO(LectureScheduleDomain domain) {
        return new LectureScheduleDTO(domain.getScheduleId(), domain.getLectureId(), domain.getDate(), domain.getCapacity());
    }

    public static LectureScheduleDomain toDomain(LectureSchedule entity) {
        return new LectureScheduleDomain(entity.getScheduleId(), entity.getLectureId(), entity.getDate(), entity.getCapacity());
    }

    public static LectureSchedule toEntity(LectureScheduleDomain domain) {
        return new LectureSchedule(domain.getScheduleId(), domain.getLectureId(), domain.getDate(), domain.getCapacity());
    }
}
