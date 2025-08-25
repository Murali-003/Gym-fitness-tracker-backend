// package com.examly.springapp.model;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import jakarta.persistence.*;
// import jakarta.validation.constraints.NotNull;
// import java.time.LocalDate;

// @Entity
// @Table(name = "user_plan_progress")
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// public class UserPlanProgress {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;

//     @NotNull
//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "user_id")
//     @JsonIgnoreProperties({"trainer"})
//     private User user;

//     @NotNull
//     @ManyToOne(fetch = FetchType.LAZY)
//     @JoinColumn(name = "workout_plan_id")
//     @JsonIgnoreProperties({"exercises", "member", "createdBy"})
//     private WorkoutPlan workoutPlan;

//     @NotNull
//     private LocalDate date;

//     @NotNull
//     private int progressPercentage;
// }

package com.examly.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "user_plan_progress")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class UserPlanProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @JsonIgnoreProperties({"trainer", "hibernateLazyInitializer", "handler"})
    private User user;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "workout_plan_id")
    @JsonIgnoreProperties({"exercises", "member", "createdBy", "hibernateLazyInitializer", "handler"})
    private WorkoutPlan workoutPlan;

    @NotNull
    private LocalDate date;

    @NotNull
    @Min(value = 0, message = "Progress percentage must be at least 0")
    @Max(value = 100, message = "Progress percentage cannot exceed 100")
    private int progressPercentage;
}