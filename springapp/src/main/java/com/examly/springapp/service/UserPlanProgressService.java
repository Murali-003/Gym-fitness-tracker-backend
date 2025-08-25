package com.examly.springapp.service;

import com.examly.springapp.model.UserPlanProgress;
import java.util.List;

public interface UserPlanProgressService {
    UserPlanProgress saveProgress(UserPlanProgress progress);
    List<UserPlanProgress> getAllProgress();
    UserPlanProgress getProgressById(Long id);
    void deleteProgress(Long id);
    List<UserPlanProgress> getProgressByUserId(Long userId);
}
