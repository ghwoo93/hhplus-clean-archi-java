package io.hhplus.clean.architect.lecture.aggregate.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LectureVO {
    private final Long lectureId;
    private final String title;
    private final Timestamp date;
    private final int capacity;
}
