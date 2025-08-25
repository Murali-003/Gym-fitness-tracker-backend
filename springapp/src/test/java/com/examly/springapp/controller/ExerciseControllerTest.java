// package com.examly.springapp.controller;

// import com.examly.springapp.model.Exercise;
// import com.examly.springapp.model.enums.Difficulty;
// import com.examly.springapp.model.WorkoutPlan;
// import com.examly.springapp.model.User;
// import com.examly.springapp.model.enums.Role;
// import com.examly.springapp.service.ExerciseService;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import java.time.LocalDate;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
// import java.util.Arrays;

// @WebMvcTest(ExerciseController.class)
// public class ExerciseControllerTest {
//     @Autowired
//     private MockMvc mockMvc;
//     @MockBean
//     private ExerciseService exerciseService;
//     @Autowired
//     private ObjectMapper objectMapper;

//     @Test
//     public void service_testAddExerciseToWorkoutPlanSuccess() throws Exception {
//         Exercise exercise = Exercise.builder().exerciseId(1L).name("Push-ups").sets(3).reps(15).build();
//         Mockito.when(exerciseService.addExerciseToPlan(Mockito.anyLong(), Mockito.any(Exercise.class))).thenReturn(exercise);
//         String body = objectMapper.writeValueAsString(exercise);
//         mockMvc.perform(post("/api/workout-plans/1/exercises").contentType(MediaType.APPLICATION_JSON).content(body))
//                 .andExpect(status().isCreated()).andExpect(jsonPath("$.name").value("Push-ups"));
//     }

//     @Test
//     public void exception_testAddExerciseValidationFail() throws Exception {
//         // sets < 1
//         String body = "{\"name\":\"Squats\",\"sets\":0,\"reps\":10}";
//         mockMvc.perform(post("/api/workout-plans/1/exercises").contentType(MediaType.APPLICATION_JSON).content(body))
//                 .andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").exists());
//     }

//     @Test
//     public void service_testGetExercisesForPlan() throws Exception {
//         Exercise exercise = Exercise.builder().exerciseId(1L).name("Push-ups").sets(3).reps(15).build();
//         Mockito.when(exerciseService.getExercisesByWorkoutPlan(1L)).thenReturn(Arrays.asList(exercise));
//         mockMvc.perform(get("/api/workout-plans/1/exercises")).andExpect(status().isOk()).andExpect(jsonPath("$[0].name").value("Push-ups"));
//     }
// }
