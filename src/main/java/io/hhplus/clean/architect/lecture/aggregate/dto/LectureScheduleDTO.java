package io.hhplus.clean.architect.lecture.aggregate.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LectureScheduleDTO {
    private Long scheduleId;
    private Long lectureId;
    private Timestamp date;
    private int capacity;
}

