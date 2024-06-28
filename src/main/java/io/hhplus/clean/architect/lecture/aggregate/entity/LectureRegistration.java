package io.hhplus.clean.architect.lecture.aggregate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;

@Entity
@IdClass(LectureRegistrationId.class)
public record LectureRegistration(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long registrationId,
    Long userId,
    Long scheduleId,
    Timestamp registrationDate
) implements Serializable {}