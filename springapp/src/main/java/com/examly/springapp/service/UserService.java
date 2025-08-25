
package com.examly.springapp.service;

import com.examly.springapp.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User saveUser(User user);
    User updateUser(Long id, User updatedUser);
    boolean deleteUser(Long id);
    Page<User> getPaginatedUsers(Pageable pageable);
    User register(User user);
    User login(String email, String password);

    User assignMemberToTrainer(Long memberId, Long trainerId);
    List<User> getMembersForTrainer(Long trainerId);
}
