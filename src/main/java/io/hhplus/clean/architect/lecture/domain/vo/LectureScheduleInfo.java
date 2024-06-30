package io.hhplus.clean.architect.lecture.domain.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
@ToString
public class LectureScheduleInfo {
    private Timestamp date;
    private int capacity;
}

