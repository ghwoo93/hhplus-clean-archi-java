package io.hhplus.clean.architect.lecture.aggregate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record LectureSchedule(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long scheduleId,
    Long lectureId,
    Timestamp date,
    int capacity
) implements Serializable {
    
}
