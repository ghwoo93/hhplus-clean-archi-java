-- Lecture 데이터 삽입
INSERT INTO Lecture (title) VALUES ('Spring Boot');
INSERT INTO Lecture (title) VALUES ('Java Programming');
INSERT INTO Lecture (title) VALUES ('Software Design Principles');

-- LectureSchedule 데이터 삽입
INSERT INTO LectureSchedule (lecture_id, date, capacity) VALUES (1, '2024-04-20 13:00:00', 30);
INSERT INTO LectureSchedule (lecture_id, date, capacity) VALUES (2, '2024-04-20 14:00:00', 30);
INSERT INTO LectureSchedule (lecture_id, date, capacity) VALUES (3, '2024-04-21 13:00:00', 30);
INSERT INTO LectureSchedule (lecture_id, date, capacity) VALUES (4, '2024-04-22 13:00:00', 30);
