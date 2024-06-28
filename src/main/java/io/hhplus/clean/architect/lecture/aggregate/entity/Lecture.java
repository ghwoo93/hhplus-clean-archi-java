package io.hhplus.clean.architect.lecture.aggregate.entity;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// @Entity
// public record Lecture(
//     @Id
//     @GeneratedValue(strategy = GenerationType.AUTO)
//     Long lectureId,
//     String title
// ) implements Serializable {}

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter
public class Lecture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lectureId;
    private String title;
}
