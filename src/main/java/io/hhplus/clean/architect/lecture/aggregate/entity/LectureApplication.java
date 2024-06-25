package io.hhplus.clean.architect.lecture.aggregate.entity;


import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.ForeignKey;

@Entity
public record LectureApplication(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long applicationId,

    @ManyToOne
    @JoinColumn(name = "user_id", foreignKey = @ForeignKey(name = "FK_USER"))
    User user,

    @ManyToOne
    @JoinColumn(name = "lecture_id", foreignKey = @ForeignKey(name = "FK_LECTURE"))
    Lecture lecture,

    Timestamp applicationDate
) implements Serializable {
    public LectureApplication {
        if (applicationDate == null) {
            applicationDate = new Timestamp(System.currentTimeMillis());
        }
    }
}
