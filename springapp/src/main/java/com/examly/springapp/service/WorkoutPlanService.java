package com.examly.springapp.service;

import com.examly.springapp.model.WorkoutPlan;
import com.examly.springapp.model.enums.Difficulty;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface WorkoutPlanService {
    WorkoutPlan createWorkoutPlan(WorkoutPlan plan);
    Page<WorkoutPlan> getAllWorkoutPlans(Pageable pageable);
    Optional<WorkoutPlan> getWorkoutPlanById(Long id);
    WorkoutPlan updateWorkoutPlan(Long id, WorkoutPlan updatedPlan);
    void deleteWorkoutPlan(Long id);
    
    List<WorkoutPlan> getWorkoutPlansByDifficulty(Difficulty difficulty);
    List<WorkoutPlan> getAllWorkoutPlansWithoutPagination();
    
    List<WorkoutPlan> getWorkoutPlansByTrainer(Long trainerId);
    List<WorkoutPlan> getWorkoutPlansByTrainerAndDifficulty(Long trainerId, Difficulty difficulty);
    
    List<WorkoutPlan> getWorkoutPlansByMember(Long memberId);
}