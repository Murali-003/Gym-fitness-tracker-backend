// package com.examly.springapp.service;

// import com.examly.springapp.model.User;
// import com.examly.springapp.model.UserPlanProgress;
// import com.examly.springapp.repository.UserPlanProgressRepository;
// import com.examly.springapp.repository.UserRepository;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class UserPlanProgressServiceImpl implements UserPlanProgressService {

//     private final UserPlanProgressRepository progressRepo;
//     private final UserRepository userRepo;

//     public UserPlanProgressServiceImpl(UserPlanProgressRepository progressRepo, UserRepository userRepo) {
//         this.progressRepo = progressRepo;
//         this.userRepo = userRepo;
//     }

//     @Override
//     public UserPlanProgress saveProgress(UserPlanProgress progress) {
//         return progressRepo.save(progress);
//     }

//     @Override
//     public List<UserPlanProgress> getAllProgress() {
//         return progressRepo.findAll();
//     }

//     @Override
//     public UserPlanProgress getProgressById(Long id) {
//         return progressRepo.findById(id).orElseThrow(() -> new RuntimeException("Progress not found"));
//     }

//     @Override
//     public void deleteProgress(Long id) {
//         progressRepo.deleteById(id);
//     }

//     @Override
//     public List<UserPlanProgress> getProgressByUserId(Long userId) {
//         User user = userRepo.findById(userId)
//                 .orElseThrow(() -> new RuntimeException("User not found"));
//         return progressRepo.findByUser(user);
//     }
// }

package com.examly.springapp.service;

import com.examly.springapp.model.User;
import com.examly.springapp.model.UserPlanProgress;
import com.examly.springapp.model.WorkoutPlan;
import com.examly.springapp.repository.UserPlanProgressRepository;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.repository.WorkoutPlanRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserPlanProgressServiceImpl implements UserPlanProgressService {

    private final UserPlanProgressRepository progressRepo;
    private final UserRepository userRepo;
    private final WorkoutPlanRepository workoutPlanRepo;

    public UserPlanProgressServiceImpl(UserPlanProgressRepository progressRepo, 
                                     UserRepository userRepo,
                                     WorkoutPlanRepository workoutPlanRepo) {
        this.progressRepo = progressRepo;
        this.userRepo = userRepo;
        this.workoutPlanRepo = workoutPlanRepo;
    }

    @Override
    public UserPlanProgress saveProgress(UserPlanProgress progress) {
        // Ensure user and workout plan are properly loaded
        if (progress.getUser() != null && progress.getUser().getUserId() != null) {
            User user = userRepo.findById(progress.getUser().getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            progress.setUser(user);
        }
        
        if (progress.getWorkoutPlan() != null && progress.getWorkoutPlan().getPlanId() != null) {
            WorkoutPlan plan = workoutPlanRepo.findById(progress.getWorkoutPlan().getPlanId())
                    .orElseThrow(() -> new RuntimeException("Workout plan not found"));
            progress.setWorkoutPlan(plan);
        }
        
        return progressRepo.save(progress);
    }

    @Override
    public List<UserPlanProgress> getAllProgress() {
        return progressRepo.findAll();
    }

    @Override
    public UserPlanProgress getProgressById(Long id) {
        return progressRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Progress not found"));
    }

    @Override
    public void deleteProgress(Long id) {
        progressRepo.deleteById(id);
    }

    @Override
    public List<UserPlanProgress> getProgressByUserId(Long userId) {
        return progressRepo.findByUserUserId(userId);
    }
    
    public List<UserPlanProgress> getProgressByTrainerId(Long trainerId) {
        return progressRepo.findByUserTrainerUserId(trainerId);
    }
}