package com.examly.springapp.controller;

import com.examly.springapp.exception.AdminAlreadyExistsException;
import com.examly.springapp.model.User;
import com.examly.springapp.model.enums.Role;
import com.examly.springapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin(origins = "https://gym-fitness-management-a3t7.vercel.app") 
@RestController
@RequestMapping("/api/users")
public class UserController 
{
    private static final String ERROR_KEY = "error";
    private final UserService userService;
    public UserController(UserService service) { this.userService = service; }

    @PostMapping("/{memberId}/assign/{trainerId}")
    public ResponseEntity<User> assignMemberToTrainer(
            @PathVariable Long memberId,
            @PathVariable Long trainerId) {
        User updatedMember = userService.assignMemberToTrainer(memberId, trainerId);
        return ResponseEntity.ok(updatedMember);
    }

    @GetMapping("/trainer/{trainerId}/members")
    public ResponseEntity<List<User>> getMembersForTrainer(@PathVariable Long trainerId) {
        return ResponseEntity.ok(userService.getMembersForTrainer(trainerId));
    }

    // @PostMapping("/register")
    // public ResponseEntity<?> register(@RequestBody User user) {
    //     try {
    //         User saved = userService.register(user);
            
    //         // Return different messages based on role and approval status
    //         String message;
    //         if (saved.getRole() == Role.MEMBER) {
    //             message = "Member registered successfully! You can now login.";
    //         } else if (saved.getRole() == Role.TRAINER) {
    //             message = "Trainer registration submitted successfully! Awaiting admin approval.";
    //         } else {
    //             message = "Admin registration submitted successfully! Awaiting approval.";
    //         }
            
    //         return ResponseEntity.status(HttpStatus.CREATED)
    //             .body(Map.of("message", message, "user", saved));
    //     } catch (Exception e) {
    //         return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
    //     }
    // }
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        User saved = userService.register(user);
        String message = switch (saved.getRole()) {
            case MEMBER -> "Member registered successfully! You can now login.";
            case TRAINER -> "Trainer registration submitted successfully! Awaiting admin approval.";
            default -> "Admin registration submitted successfully! Awaiting approval.";
        };
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(Map.of("message", message, "user", saved));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String email = credentials.get("email");
            String password = credentials.get("password");
            User user = userService.login(email, password);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", e.getMessage()));
        }
    }
    
    @PutMapping("/{id}/approve")
    public ResponseEntity<?> approveUser(@PathVariable Long id) {
        Optional<User> userOpt = userService.getUserById(id);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }
        User user = userOpt.get();
        user.setApproved(true);
        userService.saveUser(user);
        return ResponseEntity.ok(Map.of("message", "User approved successfully"));
    }

    @PutMapping("/{id}/reject")
    public ResponseEntity<?> rejectUser(@PathVariable Long id) {
        if (userService.deleteUser(id)) {
            return ResponseEntity.ok(Map.of("message", "User rejected and deleted"));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "User not found"));
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getUserById(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        if (user.isPresent()) {
            return ResponseEntity.ok(user.get());
        } else {
            return ResponseEntity.status(404).body(Map.of(ERROR_KEY, "User not found"));
        }
    }

    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.status(201).body(userService.saveUser(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @Valid @RequestBody User updatedUser) {
        try {
            User updated = userService.updateUser(id, updatedUser);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) {
        try {
            if (userService.deleteUser(id)) {
                return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
            } else {
                return ResponseEntity.status(404).body(Map.of(ERROR_KEY, "User not found"));
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }

    @GetMapping("/paginated")
    public Page<User> getPaginatedUsers(@PageableDefault(size = 5, sort = "username") Pageable pageable) {
        return userService.getPaginatedUsers(pageable);
    }

    @ExceptionHandler(AdminAlreadyExistsException.class)
        public ResponseEntity<Map<String, String>> handleAdminExists(AdminAlreadyExistsException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGeneric(Exception e) {
        return ResponseEntity.badRequest().body(Map.of(ERROR_KEY, "Something went wrong"));
    }
}