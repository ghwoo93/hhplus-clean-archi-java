package io.hhplus.clean.architect.lecture.aggregate.domain;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LectureRegistrationDomain {
    private Long registrationId;
    private Long userId;
    private Long scheduleId;
    private Timestamp registrationDate;
}
