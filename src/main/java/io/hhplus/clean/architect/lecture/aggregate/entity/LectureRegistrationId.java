package io.hhplus.clean.architect.lecture.aggregate.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@EqualsAndHashCode
public class LectureRegistrationId implements Serializable {
    private Long registrationId;
    private Long userId;
}