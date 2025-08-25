// package com.examly.springapp.repository;

// import com.examly.springapp.model.User;
// import com.examly.springapp.model.UserPlanProgress;
// import com.examly.springapp.model.WorkoutPlan;
// import org.springframework.data.jpa.repository.JpaRepository;
// import java.util.List;

// public interface UserPlanProgressRepository extends JpaRepository<UserPlanProgress, Long> {
//     List<UserPlanProgress> findByUser(User user);
//     List<UserPlanProgress> findByWorkoutPlan(WorkoutPlan workoutPlan);
// }

package com.examly.springapp.repository;

import com.examly.springapp.model.User;
import com.examly.springapp.model.UserPlanProgress;
import com.examly.springapp.model.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserPlanProgressRepository extends JpaRepository<UserPlanProgress, Long> {
    
    @Query("SELECT upp FROM UserPlanProgress upp WHERE upp.user.userId = :userId ORDER BY upp.date DESC")
    List<UserPlanProgress> findByUserUserId(@Param("userId") Long userId);
    
    List<UserPlanProgress> findByUser(User user);
    List<UserPlanProgress> findByWorkoutPlan(WorkoutPlan workoutPlan);
    
    @Query("SELECT upp FROM UserPlanProgress upp WHERE upp.user.trainer.userId = :trainerId ORDER BY upp.date DESC")
    List<UserPlanProgress> findByUserTrainerUserId(@Param("trainerId") Long trainerId);
}