package io.hhplus.clean.architect.lecture.domain.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class LectureRegistrationId implements Serializable {
    private Long registrationId;
    private Long userId;
}