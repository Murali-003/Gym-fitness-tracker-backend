// package com.examly.springapp.model;

// import com.examly.springapp.model.enums.Difficulty;
// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import jakarta.persistence.*;
// import jakarta.validation.constraints.NotNull;
// import lombok.*;
// import java.time.LocalDate;
// import java.util.ArrayList;
// import java.util.List;

// @Entity
// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// public class WorkoutPlan {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long planId;

//     private String title;
//     private String description;

//     @Enumerated(EnumType.STRING)
//     @NotNull(message = "Difficulty is required")
//     private Difficulty difficulty;

//     @ManyToOne
//     @JoinColumn(name = "created_by")
//     @NotNull(message = "CreatedBy is required")
//     @JsonIgnoreProperties({"trainer"})
//     private User createdBy;

//     private LocalDate creationDate;

//     @Builder.Default
//     @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
//     @JsonIgnoreProperties("workoutPlan")
//     private List<Exercise> exercises = new ArrayList<>();

//     @ManyToOne
//     @JoinColumn(name = "member_id")
//     @NotNull(message = "Member is required")
//     @JsonIgnoreProperties({"trainer"})
//     private User member;
// }

package com.examly.springapp.model;

import com.examly.springapp.model.enums.Difficulty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class WorkoutPlan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long planId;

    @Column(nullable = false)
    private String title;
    
    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Difficulty is required")
    private Difficulty difficulty;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "created_by", nullable = false)
    @NotNull(message = "CreatedBy is required")
    @JsonIgnoreProperties({"trainer", "hibernateLazyInitializer", "handler"})
    private User createdBy;

    @Column(nullable = false)
    private LocalDate creationDate;

    @Builder.Default
    @OneToMany(mappedBy = "workoutPlan", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"workoutPlan", "hibernateLazyInitializer", "handler"})
    private List<Exercise> exercises = new ArrayList<>();

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id", nullable = false)
    @NotNull(message = "Member is required")
    @JsonIgnoreProperties({"trainer", "hibernateLazyInitializer", "handler"})
    private User member;

    // Helper method to add exercise
    public void addExercise(Exercise exercise) {
        exercises.add(exercise);
        exercise.setWorkoutPlan(this);
    }

    // Helper method to remove exercise
    public void removeExercise(Exercise exercise) {
        exercises.remove(exercise);
        exercise.setWorkoutPlan(null);
    }
}