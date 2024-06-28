package io.hhplus.clean.architect.lecture.aggregate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Entity
// @IdClass(LectureRegistrationId.class)
// public record LectureRegistration(
//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO)
//     Long registrationId,
//     Long userId,
//     Long scheduleId,
//     Timestamp registrationDate
// ) implements Serializable {}
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
@IdClass(LectureRegistrationId.class)
public class LectureRegistration implements Serializable {   
    // private LectureRegistrationId id;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long registrationId;

    @Id
    private Long userId;
    private Long scheduleId;
    private Timestamp registrationDate;
}