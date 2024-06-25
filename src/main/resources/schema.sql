-- Users 테이블 생성 (User 대신 Users 사용)
CREATE TABLE Users (
    user_id BIGINT PRIMARY KEY,
    username VARCHAR(255) NOT NULL
);

-- Lectures 테이블 생성 (Lecture 대신 Lectures 사용)
CREATE TABLE Lectures (
    lecture_id BIGINT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    date TIMESTAMP NOT NULL,
    capacity INT DEFAULT 30 NOT NULL
);

-- LectureApplications 테이블 생성 (LectureApplication 대신 LectureApplications 사용)
CREATE TABLE LectureApplications (
    application_id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT,
    lecture_id BIGINT,
    application_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id),
    FOREIGN KEY (lecture_id) REFERENCES Lectures(lecture_id),
    UNIQUE (user_id, lecture_id)
);