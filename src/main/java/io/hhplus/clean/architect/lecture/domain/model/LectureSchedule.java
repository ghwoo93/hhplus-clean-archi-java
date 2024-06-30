package io.hhplus.clean.architect.lecture.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "LECTURE_SCHEDULE")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class LectureSchedule implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;
    private Long lectureId;
    private Timestamp date;
    private int capacity;
}
