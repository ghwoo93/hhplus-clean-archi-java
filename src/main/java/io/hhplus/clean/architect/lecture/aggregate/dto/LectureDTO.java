package io.hhplus.clean.architect.lecture.aggregate.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LectureDTO {
    private Long lectureId;
    private String title;
}