// package com.examly.springapp.model;

// import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
// import jakarta.persistence.*;
// import jakarta.validation.constraints.Min;
// import jakarta.validation.constraints.NotBlank;
// import lombok.*;

// @Entity
// @Data
// @Builder
// @NoArgsConstructor
// @AllArgsConstructor
// @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
// public class Exercise {

//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long exerciseId;

//     @NotBlank
//     private String name;

//     @Min(1)
//     private int sets;

//     @Min(1)
//     private int reps;

//     @ManyToOne(fetch = FetchType.EAGER) 
//     @JoinColumn(name = "workout_plan_id")
//     @JsonIgnoreProperties("exercises")  
//     private WorkoutPlan workoutPlan;
// }

package com.examly.springapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Exercise {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long exerciseId;

    @NotBlank(message = "Exercise name is required")
    private String name;

    @Min(value = 1, message = "Sets must be at least 1")
    private int sets;

    @Min(value = 1, message = "Reps must be at least 1")
    private int reps;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_plan_id", nullable = false)
    @JsonIgnoreProperties({"exercises", "hibernateLazyInitializer", "handler"})
    private WorkoutPlan workoutPlan;
}