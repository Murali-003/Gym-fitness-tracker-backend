package com.examly.springapp.service;

import com.examly.springapp.model.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ExerciseService {
    Exercise addExerciseToPlan(Long planId,Exercise exercise);
    List<Exercise> getExercisesByWorkoutPlan(Long planId);
    Page<Exercise> getPagedExercisesByWorkoutPlan(Long planId, Pageable pageable);
    Exercise getExerciseById(Long planId, Long exerciseId);
    Exercise updateExercise(Long planId, Long exerciseId, Exercise updatedExercise);
    void deleteExercise(Long planId, Long exerciseId);
}
    