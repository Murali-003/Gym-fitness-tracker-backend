// package com.examly.springapp.service;

// import com.examly.springapp.model.Exercise;
// import com.examly.springapp.model.WorkoutPlan;
// import com.examly.springapp.repository.ExerciseRepository;
// import com.examly.springapp.repository.WorkoutPlanRepository;
// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class ExerciseServiceImpl implements ExerciseService {

//     private final ExerciseRepository exerciseRepo;
//     private final WorkoutPlanRepository planRepo;

//     public ExerciseServiceImpl(ExerciseRepository exerciseRepo, WorkoutPlanRepository planRepo) {
//         this.exerciseRepo = exerciseRepo;
//         this.planRepo = planRepo;
//     }

//     @Override
//     public Exercise addExerciseToPlan(Long planId,Exercise exercise) {
//         WorkoutPlan plan = planRepo.findById(planId)
//                 .orElseThrow(() -> new RuntimeException("WorkoutPlan not found with id: " + planId));

//         exercise.setWorkoutPlan(plan); 
//         return exerciseRepo.save(exercise);
//     }

//     @Override
//     public List<Exercise> getExercisesByWorkoutPlan(Long planId) {
//         return exerciseRepo.findByWorkoutPlan_PlanId(planId);
//     }

//     @Override
//     public Page<Exercise> getPagedExercisesByWorkoutPlan(Long planId, Pageable pageable) {
//         return exerciseRepo.findByWorkoutPlan_PlanId(planId, pageable);
//     }

//     @Override
//     public Exercise getExerciseById(Long planId, Long exerciseId) {
//         Exercise exercise = exerciseRepo.findById(exerciseId).orElseThrow();
//         if (!exercise.getWorkoutPlan().getPlanId().equals(planId)) {
//             throw new IllegalArgumentException("Exercise does not belong to given workout plan");
//         }
//         return exercise;
//     }

//     @Override
//     public Exercise updateExercise(Long planId, Long exerciseId, Exercise updatedExercise) {
//         Exercise existing = getExerciseById(planId, exerciseId);
//         existing.setName(updatedExercise.getName());
//         existing.setReps(updatedExercise.getReps());
//         existing.setSets(updatedExercise.getSets());
//         return exerciseRepo.save(existing);
//     }

//     @Override
//     public void deleteExercise(Long planId, Long exerciseId) {
//         Exercise existing = getExerciseById(planId, exerciseId);
//         exerciseRepo.delete(existing);
//     }
// }
package com.examly.springapp.service;

import com.examly.springapp.model.Exercise;
import com.examly.springapp.model.WorkoutPlan;
import com.examly.springapp.repository.ExerciseRepository;
import com.examly.springapp.repository.WorkoutPlanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepo;
    private final WorkoutPlanRepository planRepo;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepo, WorkoutPlanRepository planRepo) {
        this.exerciseRepo = exerciseRepo;
        this.planRepo = planRepo;
    }

    @Override
    @Transactional
    public Exercise addExerciseToPlan(Long planId, Exercise exercise) {
        WorkoutPlan plan = planRepo.findById(planId)
                .orElseThrow(() -> new RuntimeException("WorkoutPlan not found with id: " + planId));

        // Clear the exerciseId to let JPA auto-generate it
        exercise.setExerciseId(null);
        exercise.setWorkoutPlan(plan);
        
        Exercise saved = exerciseRepo.save(exercise);
        
        // Add to the plan's exercise list to maintain bidirectional relationship
        plan.addExercise(saved);
        planRepo.save(plan);
        
        return saved;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Exercise> getExercisesByWorkoutPlan(Long planId) {
        // Verify the workout plan exists
        planRepo.findById(planId)
                .orElseThrow(() -> new RuntimeException("WorkoutPlan not found with id: " + planId));
        
        return exerciseRepo.findByWorkoutPlan_PlanId(planId);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Exercise> getPagedExercisesByWorkoutPlan(Long planId, Pageable pageable) {
        // Verify the workout plan exists
        planRepo.findById(planId)
                .orElseThrow(() -> new RuntimeException("WorkoutPlan not found with id: " + planId));
        
        return exerciseRepo.findByWorkoutPlan_PlanId(planId, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Exercise getExerciseById(Long planId, Long exerciseId) {
        Exercise exercise = exerciseRepo.findById(exerciseId)
                .orElseThrow(() -> new RuntimeException("Exercise not found with id: " + exerciseId));
        
        if (!exercise.getWorkoutPlan().getPlanId().equals(planId)) {
            throw new IllegalArgumentException("Exercise does not belong to the specified workout plan");
        }
        
        return exercise;
    }

    @Override
    @Transactional
    public Exercise updateExercise(Long planId, Long exerciseId, Exercise updatedExercise) {
        Exercise existing = getExerciseById(planId, exerciseId);
        
        existing.setName(updatedExercise.getName());
        existing.setReps(updatedExercise.getReps());
        existing.setSets(updatedExercise.getSets());
        
        return exerciseRepo.save(existing);
    }

    @Override
    @Transactional
    public void deleteExercise(Long planId, Long exerciseId) {
        Exercise existing = getExerciseById(planId, exerciseId);
        
        WorkoutPlan plan = existing.getWorkoutPlan();
        plan.removeExercise(existing);
        
        exerciseRepo.delete(existing);
        planRepo.save(plan);
    }
}