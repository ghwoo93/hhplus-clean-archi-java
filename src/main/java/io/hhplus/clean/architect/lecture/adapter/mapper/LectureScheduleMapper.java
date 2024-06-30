package io.hhplus.clean.architect.lecture.adapter.mapper;

import io.hhplus.clean.architect.lecture.domain.dto.LectureScheduleDTO;
import io.hhplus.clean.architect.lecture.domain.model.LectureSchedule;

public class LectureScheduleMapper {

    public static LectureScheduleDTO toDTO(LectureSchedule schedule) {
        return new LectureScheduleDTO(
                schedule.getScheduleId(),
                schedule.getLectureId(),
                schedule.getDate(),
                schedule.getCapacity()
        );
    }

    public static LectureSchedule toEntity(LectureScheduleDTO dto) {
        return new LectureSchedule(
                dto.getScheduleId(),
                dto.getLectureId(),
                dto.getDate(), 
                dto.getCapacity()
        );
    }
}
