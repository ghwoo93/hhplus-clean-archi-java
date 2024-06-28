package io.hhplus.clean.architect.lecture.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.hhplus.clean.architect.lecture.aggregate.dto.LectureDTO;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureRegistrationDTO;
import io.hhplus.clean.architect.lecture.aggregate.dto.LectureScheduleDTO;
import io.hhplus.clean.architect.lecture.exception.LectureBusinessException;
import io.hhplus.clean.architect.lecture.service.LectureService;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {

    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    // 특강 목록 조회 API
    @GetMapping
    public List<LectureDTO> getAllLectures() {
        return lectureService.getAllLectures();
    }

    @GetMapping("/{lectureId}/schedules")
    public List<LectureScheduleDTO> getLectureSchedules(@PathVariable Long lectureId) {
        return lectureService.getLectureSchedules(lectureId);
    }

    // 특강 신청 API
    @PostMapping("/apply")
    public ResponseEntity<String> applyForLecture(@RequestParam Long userId, @RequestParam Long scheduleId) {
        boolean success;
        try {
            success = lectureService.applyForLecture(userId, scheduleId);
            if (success) {
                return ResponseEntity.ok("Application successful");
            } else {
                throw new LectureBusinessException("Application failed");
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Application failed");
        }
    }

    // 특강 신청 완료 여부 조회 API
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
