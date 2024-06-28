package io.hhplus.clean.architect.lecture.aggregate.entity;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Entity
// public record LectureSchedule(
//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO)
//     Long scheduleId,
//     Long lectureId,
//     Timestamp date,
//     int capacity
// ) implements Serializable { }
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class LectureSchedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;
    private Long lectureId;
    private Timestamp date;
    private int capacity;
}
