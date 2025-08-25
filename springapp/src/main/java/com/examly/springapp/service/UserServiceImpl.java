package com.examly.springapp.service;

import com.examly.springapp.exception.AdminAlreadyExistsException;
import com.examly.springapp.model.User;
import com.examly.springapp.model.enums.Role;
import com.examly.springapp.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository repo;

    public UserServiceImpl(UserRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<User> getAllUsers() {
        return repo.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return repo.findById(id);
    }

    @Override
    public User saveUser(User user) {
        if (user.getRole() == Role.ADMIN) {
            boolean exists = repo.existsByRole(Role.ADMIN);
            if (exists) {
                throw new AdminAlreadyExistsException("Only one admin account is allowed.");
            }
        }
        return repo.save(user);
    }
   
    @Override
    public User updateUser(Long id, User updatedUser) {
        User existing = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        if (updatedUser.getRole() == Role.ADMIN 
                && repo.existsByRole(Role.ADMIN) 
                && existing.getRole() != Role.ADMIN) {
                throw new AdminAlreadyExistsException("Only one admin account is allowed.");
        }
        existing.setUsername(updatedUser.getUsername());
        existing.setEmail(updatedUser.getEmail());
        existing.setRole(updatedUser.getRole());
        existing.setJoinDate(updatedUser.getJoinDate());
        // Keep the phone number update
        if (updatedUser.getPhoneNumber() != null) {
            existing.setPhoneNumber(updatedUser.getPhoneNumber());
        }
        return repo.save(existing);
    }

    @Override
    public User register(User user) {
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalStateException("Password cannot be empty");
        }
        user.setJoinDate(LocalDate.now());
        
        // Auto-approve members, require approval for trainers and admins
        if (user.getRole() == Role.MEMBER) {
            user.setApproved(true);
        } else {
            user.setApproved(false); // TRAINER and ADMIN need approval
        }
        
        return saveUser(user);
    }

    @Override
    public User login(String email, String password) {
        return repo.findByEmail(email)
                .map(u -> {
                    if (u.getPassword() == null || !u.getPassword().equals(password)) {
                        throw new RuntimeException("Invalid email or password");
                    }
                    if (!u.isApproved()) {
                        // Different messages based on role
                        if (u.getRole() == Role.TRAINER) {
                            throw new RuntimeException("Your trainer account is pending admin approval");
                        } else if (u.getRole() == Role.ADMIN) {
                            throw new RuntimeException("Your admin account is pending approval");
                        } else {
                            throw new RuntimeException("Your account is pending approval by admin");
                        }
                    }
                    return u;
                })
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));
    }

    @Override
    public boolean deleteUser(Long id) {
        return repo.findById(id).map(user -> {
            if (user.getRole() == Role.ADMIN) {
                throw new AdminAlreadyExistsException("Admin account cannot be deleted.");
            }
            repo.delete(user);
            return true;
        }).orElse(false);
    }

    @Override
    public Page<User> getPaginatedUsers(Pageable pageable) {
        return repo.findAll(pageable);
    }

    @Override
    public User assignMemberToTrainer(Long memberId, Long trainerId) {
        User member = repo.findById(memberId)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        User trainer = repo.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        if (member.getRole() != Role.MEMBER || !member.isApproved()) {
            throw new RuntimeException("User is not an approved member");
        }
        if (trainer.getRole() != Role.TRAINER) {
            throw new RuntimeException("Assigned user is not a trainer");
        }

        member.setTrainer(trainer);
        return repo.save(member);
    }

    @Override
    public List<User> getMembersForTrainer(Long trainerId) {
        User trainer = repo.findById(trainerId)
                .orElseThrow(() -> new RuntimeException("Trainer not found"));

        if (trainer.getRole() != Role.TRAINER) {
            throw new RuntimeException("User is not a trainer");
        }
        return repo.findByTrainer(trainer);
    }
}