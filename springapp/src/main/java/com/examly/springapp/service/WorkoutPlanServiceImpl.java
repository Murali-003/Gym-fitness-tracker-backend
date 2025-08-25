package com.examly.springapp.service;

import com.examly.springapp.model.User;
import com.examly.springapp.model.WorkoutPlan;
import com.examly.springapp.model.enums.Difficulty;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.repository.WorkoutPlanRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final UserRepository userRepository;

    public WorkoutPlanServiceImpl(WorkoutPlanRepository workoutPlanRepository, UserRepository userRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.userRepository = userRepository;
    }

    @Override
    public WorkoutPlan createWorkoutPlan(WorkoutPlan workoutPlan) {
        Long trainerId = workoutPlan.getCreatedBy().getUserId();
        Long memberId = workoutPlan.getMember().getUserId();

        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found with ID: " + trainerId));
        User member = userRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found with ID: " + memberId));

        if (member.getTrainer() == null || !member.getTrainer().getUserId().equals(trainerId)) {
            throw new RuntimeException("Trainer is not assigned to this member");
        }

        workoutPlan.setCreatedBy(trainer);
        workoutPlan.setMember(member);

        return workoutPlanRepository.save(workoutPlan);
    }

    @Override
    public Page<WorkoutPlan> getAllWorkoutPlans(Pageable pageable) {
        return workoutPlanRepository.findAll(pageable);
    }

    @Override
    public Optional<WorkoutPlan> getWorkoutPlanById(Long id) {
        return workoutPlanRepository.findByIdWithExercises(id);
    }

    @Override
    public List<WorkoutPlan> getWorkoutPlansByDifficulty(Difficulty difficulty) {
        return workoutPlanRepository.findByDifficultyWithExercises(difficulty);
    }

    @Override
    public List<WorkoutPlan> getAllWorkoutPlansWithoutPagination() {
        return workoutPlanRepository.findAllWithExercises();
    }

    @Override
    public WorkoutPlan updateWorkoutPlan(Long id, WorkoutPlan updatedPlan) {
        return workoutPlanRepository.findById(id).map(existingPlan -> {
            existingPlan.setTitle(updatedPlan.getTitle());
            existingPlan.setDescription(updatedPlan.getDescription());
            existingPlan.setDifficulty(updatedPlan.getDifficulty());
            existingPlan.setCreationDate(updatedPlan.getCreationDate());

            Long userId = updatedPlan.getCreatedBy().getUserId();
            User fullUser = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));
            existingPlan.setCreatedBy(fullUser);

            return workoutPlanRepository.save(existingPlan);
        }).orElseThrow(() -> new RuntimeException("WorkoutPlan not found with id: " + id));
    }

    @Override
    public void deleteWorkoutPlan(Long id) {
        workoutPlanRepository.deleteById(id);
    }

    @Override
    public List<WorkoutPlan> getWorkoutPlansByTrainer(Long trainerId) {
        return workoutPlanRepository.findByCreatedByUserIdWithExercises(trainerId);
    }

    @Override
    public List<WorkoutPlan> getWorkoutPlansByTrainerAndDifficulty(Long trainerId, Difficulty difficulty) {
        return workoutPlanRepository.findByCreatedByUserIdAndDifficultyWithExercises(trainerId, difficulty);
    }

    @Override
    public List<WorkoutPlan> getWorkoutPlansByMember(Long memberId) {
        return workoutPlanRepository.findByMemberUserIdWithExercises(memberId);
    }
}