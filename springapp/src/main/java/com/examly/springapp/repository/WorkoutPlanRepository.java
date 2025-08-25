package com.examly.springapp.repository;

import com.examly.springapp.model.WorkoutPlan;
import com.examly.springapp.model.enums.Difficulty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;   
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    @Query("SELECT DISTINCT wp FROM WorkoutPlan wp LEFT JOIN FETCH wp.exercises WHERE wp.planId = :planId")
    Optional<WorkoutPlan> findByIdWithExercises(@Param("planId") Long planId);

    @Query("SELECT DISTINCT wp FROM WorkoutPlan wp LEFT JOIN FETCH wp.exercises WHERE wp.difficulty = :difficulty")
    List<WorkoutPlan> findByDifficultyWithExercises(@Param("difficulty") Difficulty difficulty);

    @Query("SELECT DISTINCT wp FROM WorkoutPlan wp LEFT JOIN FETCH wp.exercises")
    List<WorkoutPlan> findAllWithExercises();

    @Query("SELECT DISTINCT wp FROM WorkoutPlan wp LEFT JOIN FETCH wp.exercises " +
            "WHERE wp.createdBy.userId = :trainerId")
    List<WorkoutPlan> findByCreatedByUserIdWithExercises(@Param("trainerId") Long trainerId);

    @Query("SELECT DISTINCT wp FROM WorkoutPlan wp LEFT JOIN FETCH wp.exercises " +
            "WHERE wp.createdBy.userId = :trainerId AND wp.difficulty = :difficulty")
    List<WorkoutPlan> findByCreatedByUserIdAndDifficultyWithExercises(@Param("trainerId") Long trainerId,
                                                                      @Param("difficulty") Difficulty difficulty);

    @Query("SELECT DISTINCT wp FROM WorkoutPlan wp LEFT JOIN FETCH wp.exercises " +
            "WHERE wp.member.userId = :memberId")
    List<WorkoutPlan> findByMemberUserIdWithExercises(@Param("memberId") Long memberId);
}
