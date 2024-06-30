package io.hhplus.clean.architect.lecture.domain.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LectureRegistrationDTO {
    private Long registrationId;
    private Long userId;
    private Long scheduleId;
    private Timestamp registrationDate;
}
