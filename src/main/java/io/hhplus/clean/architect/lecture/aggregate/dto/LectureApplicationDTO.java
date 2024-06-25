package io.hhplus.clean.architect.lecture.aggregate.dto;

import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LectureApplicationDTO {
    private Long applicationId;
    private UserDTO user;
    private LectureDTO lecture;
    private Timestamp applicationDate;
}
