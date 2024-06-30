package io.hhplus.clean.architect.lecture.domain.model;

import java.io.Serializable;
import java.sql.Timestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "LECTURE_REGISTRATION")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@IdClass(LectureRegistrationId.class)
public class LectureRegistration implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lectureregistration_seq_gen")
    @SequenceGenerator(name = "lectureregistration_seq_gen", sequenceName = "LectureRegistration_SEQ", allocationSize = 1)
    private Long registrationId;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(name = "user_seq_gen", sequenceName = "User_SEQ", allocationSize = 1)
    private Long userId;
    private Long scheduleId;
    private Timestamp registrationDate;
}