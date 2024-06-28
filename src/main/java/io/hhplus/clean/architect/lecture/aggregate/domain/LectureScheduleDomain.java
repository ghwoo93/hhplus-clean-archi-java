package io.hhplus.clean.architect.lecture.aggregate.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LectureScheduleDomain {
    private Long scheduleId;
    private Long lectureId;
    private Timestamp date;
    private int capacity;
}

