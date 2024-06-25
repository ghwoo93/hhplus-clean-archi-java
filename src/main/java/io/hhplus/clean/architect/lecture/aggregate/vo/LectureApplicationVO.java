package io.hhplus.clean.architect.lecture.aggregate.vo;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LectureApplicationVO {
    private final Long applicationId;
    private final UserVO user;
    private final LectureVO lecture;
    private final Timestamp applicationDate;
}
