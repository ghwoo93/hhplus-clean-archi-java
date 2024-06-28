package io.hhplus.clean.architect.lecture.aggregate.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public record Lecture(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long lectureId,
    String title
) implements Serializable {}
