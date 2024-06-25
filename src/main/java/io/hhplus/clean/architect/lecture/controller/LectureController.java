package io.hhplus.clean.architect.lecture.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.hhplus.clean.architect.lecture.service.LectureService;

@RestController
@RequestMapping("/api/lectures")
public class LectureController {
    private final LectureService lectureService;

    @Autowired
    public LectureController(LectureService lectureService) {
        this.lectureService = lectureService;
    }

    @PostMapping("/apply")
    public ResponseEntity<String> applyForLecture(@RequestParam Long userId, @RequestParam Long lectureId) {
        boolean success = lectureService.applyForLecture(userId, lectureId);
        if (success) {
            return ResponseEntity.ok("Application successful");
        } else {
            return ResponseEntity.badRequest().body("Application failed");
        }
    }

    @GetMapping("/application/{userId}/{lectureId}")
    public ResponseEntity<Boolean> checkApplicationStatus(@PathVariable Long userId, @PathVariable Long lectureId) {
        boolean status = lectureService.checkApplicationStatus(userId, lectureId);
        return ResponseEntity.ok(status);
    }
}
