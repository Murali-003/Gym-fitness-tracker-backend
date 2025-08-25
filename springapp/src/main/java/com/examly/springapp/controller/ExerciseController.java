    // package com.examly.springapp.controller;

    // import com.examly.springapp.model.Exercise;
    // import com.examly.springapp.service.ExerciseService;
    // import jakarta.validation.Valid;
    // import org.springframework.data.domain.Page;
    // import org.springframework.data.domain.Pageable;
    // import org.springframework.data.web.PageableDefault;
    // import org.springframework.http.ResponseEntity;
    // import org.springframework.web.bind.annotation.*;

    // import java.util.*;

    // @RestController
    // @RequestMapping("/api/workout-plans/{planId}/exercises")
    // public class ExerciseController {
    //     public static final String VALIDATION_FAILED = "Validation failed";
    //     public static final String ERROR = "error";
    //     private final ExerciseService service;

    //     public ExerciseController(ExerciseService service) {
    //         this.service = service;
    //     }

    //     @PostMapping
    //     public ResponseEntity<Exercise> add(@PathVariable Long planId,@Valid @RequestBody Exercise exercise) {
    //         Exercise saved = service.addExerciseToPlan(planId, exercise);
    //         return ResponseEntity.status(201).body(saved);
    //     }

    //     @GetMapping
    //     public ResponseEntity<List<Exercise>> getAll(@PathVariable Long planId) {
    //         return ResponseEntity.ok(service.getExercisesByWorkoutPlan(planId));
    //     }

    //     @GetMapping("/paged")
    //     public ResponseEntity<Page<Exercise>> getPagedExercises(@PathVariable Long planId,
    //                                                             @PageableDefault(size = 5, sort = "exerciseId") Pageable pageable) {
    //         return ResponseEntity.ok(service.getPagedExercisesByWorkoutPlan(planId, pageable));
    //     }

    //     @GetMapping("/{id}")
    //     public ResponseEntity<Exercise> getById(@PathVariable Long planId, @PathVariable Long id) {
    //         return ResponseEntity.ok(service.getExerciseById(planId, id));
    //     }

    //     @PutMapping("/{id}")
    //     public ResponseEntity<Exercise> update(@PathVariable Long planId, @PathVariable Long id, @Valid @RequestBody Exercise updatedExercise) {
    //         return ResponseEntity.ok(service.updateExercise(planId, id, updatedExercise));
    //     }

    //     @DeleteMapping("/{id}")
    //     public ResponseEntity<Void> delete(@PathVariable Long planId, @PathVariable Long id) {
    //         service.deleteExercise(planId, id);
    //         return ResponseEntity.noContent().build();
    //     }

    //     @ExceptionHandler
    //     public ResponseEntity<Object> handle(Exception e) {
    //         return ResponseEntity.badRequest().body(Map.of(ERROR, VALIDATION_FAILED));
    //     }
    // }
            
    package com.examly.springapp.controller;

import com.examly.springapp.model.Exercise;
import com.examly.springapp.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "https://gym-fitness-management-a3t7.vercel.app/auth")
@RequestMapping("/api/workout-plans/{planId}/exercises")
public class ExerciseController {
    public static final String VALIDATION_FAILED = "Validation failed";
    public static final String ERROR = "error";
    private final ExerciseService service;

    public ExerciseController(ExerciseService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> add(@PathVariable Long planId, @Valid @RequestBody Exercise exercise) {
        try {
            Exercise saved = service.addExerciseToPlan(planId, exercise);
            return ResponseEntity.status(201).body(saved);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<List<Exercise>> getAll(@PathVariable Long planId) {
        try {
            List<Exercise> exercises = service.getExercisesByWorkoutPlan(planId);
            return ResponseEntity.ok(exercises);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ArrayList<>());
        }
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<Exercise>> getPagedExercises(@PathVariable Long planId,
                                                            @PageableDefault(size = 5, sort = "exerciseId") Pageable pageable) {
        return ResponseEntity.ok(service.getPagedExercisesByWorkoutPlan(planId, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable Long planId, @PathVariable Long id) {
        try {
            Exercise exercise = service.getExerciseById(planId, id);
            return ResponseEntity.ok(exercise);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long planId, @PathVariable Long id, @Valid @RequestBody Exercise updatedExercise) {
        try {
            Exercise updated = service.updateExercise(planId, id, updatedExercise);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long planId, @PathVariable Long id) {
        try {
            service.deleteExercise(planId, id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handle(Exception e) {
        return ResponseEntity.badRequest().body(Map.of(ERROR, e.getMessage()));
    }
}