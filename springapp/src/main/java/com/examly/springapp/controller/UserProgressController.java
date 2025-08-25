package com.examly.springapp.controller;

import com.examly.springapp.model.UserPlanProgress;
import com.examly.springapp.service.UserPlanProgressService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/progress")
public class UserProgressController {

    private final UserPlanProgressService progressService;

    public UserProgressController(UserPlanProgressService progressService) {
        this.progressService = progressService;
    }

    @PostMapping
    public ResponseEntity<UserPlanProgress> createProgress(@Valid @RequestBody UserPlanProgress progress) {
        return ResponseEntity.ok(progressService.saveProgress(progress));
    }

    @GetMapping
    public ResponseEntity<List<UserPlanProgress>> getAllProgress() {
        return ResponseEntity.ok(progressService.getAllProgress());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserPlanProgress> getProgressById(@PathVariable Long id) {
        return ResponseEntity.ok(progressService.getProgressById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProgress(@PathVariable Long id) {
        progressService.deleteProgress(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<UserPlanProgress>> getProgressByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(progressService.getProgressByUserId(userId));
    }
}
