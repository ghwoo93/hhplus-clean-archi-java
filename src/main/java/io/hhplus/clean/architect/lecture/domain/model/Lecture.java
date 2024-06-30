package io.hhplus.clean.architect.lecture.domain.model;

import java.io.Serializable;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "LECTURE")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Lecture implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lectureId;
    private String title;
}
