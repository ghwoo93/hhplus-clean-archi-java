package io.hhplus.clean.architect.lecture.aggregate.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LectureDomain {
    private Long lectureId;
    private String title;
}
