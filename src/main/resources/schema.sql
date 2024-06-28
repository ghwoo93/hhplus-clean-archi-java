-- Lecture 테이블 생성
CREATE TABLE Lecture (
    lecture_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

-- LectureSchedule 테이블 생성
CREATE TABLE LectureSchedule (
    schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lecture_id BIGINT NOT NULL,
    date TIMESTAMP NOT NULL,
    capacity INT NOT NULL,
    CONSTRAINT fk_lecture FOREIGN KEY (lecture_id) REFERENCES Lecture(lecture_id)
);

-- LectureRegistration 테이블 생성
CREATE TABLE LectureRegistration (
    registration_id BIGINT AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    registration_date TIMESTAMP NOT NULL,
    PRIMARY KEY (registration_id, user_id),
    CONSTRAINT fk_schedule FOREIGN KEY (schedule_id) REFERENCES LectureSchedule(schedule_id)
);
