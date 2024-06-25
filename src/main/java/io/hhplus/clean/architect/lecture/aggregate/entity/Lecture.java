package io.hhplus.clean.architect.lecture.aggregate.entity;


import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long lectureId,
    String title,
    Timestamp date,
    int capacity
) implements Serializable {
    public Lecture {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be greater than zero");
        }
    }
}
