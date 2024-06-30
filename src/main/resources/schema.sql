-- 시퀀스 생성
CREATE SEQUENCE Lecture_Registration_SEQ START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE User_SEQ START WITH 1 INCREMENT BY 1;

-- Lecture 테이블 생성
CREATE TABLE Lecture (
    lecture_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL
);

-- Lecture_Schedule 테이블 생성
CREATE TABLE Lecture_Schedule (
    schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lecture_id BIGINT NOT NULL,
    date TIMESTAMP NOT NULL,
    capacity INT NOT NULL
);

-- Lecture_Registration 테이블 생성
CREATE TABLE Lecture_Registration (
    registration_id BIGINT AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    schedule_id BIGINT NOT NULL,
    registration_date TIMESTAMP NOT NULL,
    PRIMARY KEY (registration_id, user_id)
);

