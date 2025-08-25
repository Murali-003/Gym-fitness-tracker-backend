package com.examly.springapp.controller;

import com.examly.springapp.model.WorkoutPlan;
import com.examly.springapp.model.User;
import com.examly.springapp.model.enums.Difficulty;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.service.WorkoutPlanService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:8081")
@RequestMapping("/api/workout-plans")
public class WorkoutPlanController {

    private final WorkoutPlanService service;
    private final UserRepository userRepository;

    public WorkoutPlanController(WorkoutPlanService service, UserRepository userRepository) {
        this.service = service;
        this.userRepository = userRepository;   
    }

    @PostMapping
    public ResponseEntity<?> createWorkoutPlan(@RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        String description = (String) payload.get("description");
        String difficultyStr = (String) payload.get("difficulty");
        Map<String, Object> createdByMap = (Map<String, Object>) payload.get("createdBy");
        Map<String, Object> memberMap = (Map<String, Object>) payload.get("member");
        String creationDateStr = (String) payload.get("creationDate");

        Difficulty difficulty;
        try {
            difficulty = Difficulty.valueOf(difficultyStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid difficulty"));
        }

        Long trainerId, memberId;
        try {
            trainerId = Long.valueOf(String.valueOf(createdByMap.get("userId")));
            memberId = Long.valueOf(String.valueOf(memberMap.get("userId")));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid trainer or member ID"));
        }

        LocalDate creationDate;
        try {
            creationDate = LocalDate.parse(creationDateStr);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid creationDate format"));
        }

        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found with ID: " + trainerId));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

        if (member.getTrainer() == null || !member.getTrainer().getUserId().equals(trainerId)) {
            return ResponseEntity.badRequest().body(Map.of("error", "Trainer is not assigned to this member"));
        }

        WorkoutPlan plan = WorkoutPlan.builder()
                .title(title)
                .description(description)
                .difficulty(difficulty)
                .createdBy(trainer)
                .member(member)
                .creationDate(creationDate)
                .build();

        return ResponseEntity.status(201).body(service.createWorkoutPlan(plan));
    }

    
    @GetMapping("/filter")
    public ResponseEntity<List<WorkoutPlan>> getWorkoutPlansByDifficulty(
            @RequestParam String difficulty) {

        List<WorkoutPlan> plans;

        if ("ALL".equalsIgnoreCase(difficulty)) {
            plans = service.getAllWorkoutPlansWithoutPagination();
        } else {
            Difficulty diff;
            try {
                diff = Difficulty.valueOf(difficulty.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
            }
            plans = service.getWorkoutPlansByDifficulty(diff);
        }

        return ResponseEntity.ok(plans);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<WorkoutPlan>> getAllWorkoutPlans(
            @PageableDefault(size = 5) Pageable pageable) {
        Page<WorkoutPlan> page = service.getAllWorkoutPlans(pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlan> getWorkoutPlanById(@PathVariable Long id) {
        WorkoutPlan plan = service.getWorkoutPlanById(id)
                .orElseThrow(() -> new RuntimeException("WorkoutPlan not found with id: " + id));
        return ResponseEntity.ok(plan);
    }

    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<WorkoutPlan>> getWorkoutPlansByTrainer(
        @PathVariable Long trainerId,
        @RequestParam(defaultValue = "ALL") String difficulty) 
    {
        List<WorkoutPlan> plans;
        if ("ALL".equalsIgnoreCase(difficulty)) {
            plans = service.getWorkoutPlansByTrainer(trainerId);
        } else {
            Difficulty diff;
            try {
                diff = Difficulty.valueOf(difficulty.toUpperCase());
            } catch (IllegalArgumentException e) {
                return ResponseEntity.badRequest().body(null);
            }
            plans = service.getWorkoutPlansByTrainerAndDifficulty(trainerId, diff);
        }
        return ResponseEntity.ok(plans);
    }

    // NEW ENDPOINT: Get workout plans for a specific member
    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<WorkoutPlan>> getWorkoutPlansByMember(@PathVariable Long memberId) {
        List<WorkoutPlan> plans = service.getWorkoutPlansByMember(memberId);
        return ResponseEntity.ok(plans);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlan> updateWorkoutPlan(@PathVariable Long id,
                                                         @RequestBody Map<String, Object> payload) {
        String title = (String) payload.get("title");
        String description = (String) payload.get("description");
        String difficultyStr = (String) payload.get("difficulty");
        Map<String, Object> createdByMap = (Map<String, Object>) payload.get("createdBy");
        String creationDateStr = (String) payload.get("creationDate");

        Difficulty difficulty = Difficulty.valueOf(difficultyStr.toUpperCase());
        Long userId = Long.valueOf(String.valueOf(createdByMap.get("userId")));

        WorkoutPlan updatedPlan = WorkoutPlan.builder()
                .title(title)
                .description(description)
                .difficulty(difficulty)
                .createdBy(User.builder().userId(userId).build())
                .creationDate(LocalDate.parse(creationDateStr))
                .build();

        WorkoutPlan plan = service.updateWorkoutPlan(id, updatedPlan);
        return ResponseEntity.ok(plan);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutPlan(@PathVariable Long id) {
        service.deleteWorkoutPlan(id);
        return ResponseEntity.noContent().build();
    }
}