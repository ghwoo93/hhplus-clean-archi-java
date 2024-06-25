package io.hhplus.clean.architect.lecture.aggregate.dto;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class LectureDTO {
    private Long lectureId;
    private String title;
    private Timestamp date;
    private int capacity;
}
