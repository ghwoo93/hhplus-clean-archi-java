package io.hhplus.clean.architect.lecture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.hhplus.clean.architect.lecture.aggregate.entity.Lecture;
import io.hhplus.clean.architect.lecture.aggregate.entity.LectureApplication;
import io.hhplus.clean.architect.lecture.aggregate.entity.User;
import io.hhplus.clean.architect.lecture.repository.LectureApplicationRepository;
import io.hhplus.clean.architect.lecture.repository.LectureRepository;
import io.hhplus.clean.architect.lecture.repository.UserRepository;

@Service
public class LectureService {
    private final LectureRepository lectureRepository;
    private final UserRepository userRepository;
    private final LectureApplicationRepository lectureApplicationRepository;

    @Autowired
    public LectureService(LectureRepository lectureRepository, UserRepository userRepository, LectureApplicationRepository lectureApplicationRepository) {
        this.lectureRepository = lectureRepository;
        this.userRepository = userRepository;
        this.lectureApplicationRepository = lectureApplicationRepository;
    }

    public boolean applyForLecture(Long userId, Long lectureId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new IllegalArgumentException("Invalid lecture ID"));

        if (lectureApplicationRepository.existsByUserAndLecture(user, lecture)) {
            return false; // User has already applied for this lecture
        }

        if (lectureApplicationRepository.countByLecture(lecture) >= lecture.capacity()) {
            return false; // Lecture is full
        }

        LectureApplication application = new LectureApplication(null, user, lecture, null);
        lectureApplicationRepository.save(application);

        return true; // Application successful
    }

    public boolean checkApplicationStatus(Long userId, Long lectureId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user ID"));
        Lecture lecture = lectureRepository.findById(lectureId).orElseThrow(() -> new IllegalArgumentException("Invalid lecture ID"));

        return lectureApplicationRepository.existsByUserAndLecture(user, lecture);
    }
}
