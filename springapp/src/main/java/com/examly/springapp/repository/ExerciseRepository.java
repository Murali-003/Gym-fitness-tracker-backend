package com.examly.springapp.repository;

import com.examly.springapp.model.Exercise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {
    List<Exercise> findByWorkoutPlan_PlanId(Long planId);
    Page<Exercise> findByWorkoutPlan_PlanId(Long planId, Pageable pageable);
}
