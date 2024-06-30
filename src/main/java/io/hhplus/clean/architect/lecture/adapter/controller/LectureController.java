package io.hhplus.clean.architect.lecture.adapter.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.hhplus.clean.architect.lecture.application.LectureService;
import io.hhplus.clean.architect.lecture.domain.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.domain.dto.LectureRegistrationDTO;
import io.hhplus.clean.architect.lecture.domain.dto.LectureScheduleDTO;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {

    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @GetMapping
    public List<LectureDTO> getAllLectures() {
        return lectureService.getAllLectures();
    }

    @GetMapping("/{lectureId}/schedules")
    public List<LectureScheduleDTO> getLectureSchedules(@PathVariable Long lectureId) {
        return lectureService.getLectureSchedules(lectureId);
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyForLecture(@RequestParam Long userId, @RequestParam Long scheduleId) {
        try {
            lectureService.applyForLecture(userId, scheduleId);
            return ResponseEntity.ok("Application successful");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Application failed: " + e.getMessage());
        }
    }

    @GetMapping("/application/{userId}")
    public ResponseEntity<Boolean> checkRegistrationStatus(@PathVariable Long userId) {
        boolean status = lectureService.checkRegistrationStatus(userId);
        return ResponseEntity.ok(status);
    }

    @GetMapping("/{scheduleId}/registrations")
    public List<LectureRegistrationDTO> getRegistrationHistory(@PathVariable Long scheduleId) {
        return lectureService.getRegistrationHistory(scheduleId);
    }
}
