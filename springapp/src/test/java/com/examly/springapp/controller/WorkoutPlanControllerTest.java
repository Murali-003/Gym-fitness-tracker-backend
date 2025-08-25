// package com.examly.springapp.controller;

// import com.examly.springapp.model.WorkoutPlan;
// import com.examly.springapp.model.enums.Difficulty;
// import com.examly.springapp.model.User;
// import com.examly.springapp.model.enums.Role;
// import com.examly.springapp.service.WorkoutPlanService;
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
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// @WebMvcTest(WorkoutPlanController.class)
// public class WorkoutPlanControllerTest {
//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private WorkoutPlanService workoutPlanService;

//     @Autowired
//     private ObjectMapper objectMapper;

//     @Test
//     public void controller_testCreateWorkoutPlanSuccess() throws Exception {
//         User user = User.builder().userId(1L).username("trainer").email("t@a.com").role(Role.TRAINER).build();
//         WorkoutPlan plan = WorkoutPlan.builder().planId(1L).title("A").description("desc").difficulty(Difficulty.INTERMEDIATE).createdBy(user).creationDate(LocalDate.now()).build();
//         Mockito.when(workoutPlanService.createWorkoutPlan(Mockito.any(WorkoutPlan.class))).thenReturn(plan);
//         String json = objectMapper.writeValueAsString(plan);
//         mockMvc.perform(post("/api/workout-plans").contentType(MediaType.APPLICATION_JSON).content(json))
//                 .andExpect(status().isCreated())
//                 .andExpect(jsonPath("$.title").value("A"));
//     }

//     @Test
//     public void exception_testCreateWorkoutPlanValidationFail() throws Exception {
//         // assemble the JSON string with escaped quotes for Java
//         String json = "{\"title\":\"Abc\",\"description\":\"desc\",\"difficulty\":\"INVALID\",\"createdBy\":{\"userId\":1},\"creationDate\":\"2023-06-10\"}";
//         mockMvc.perform(post("/api/workout-plans").contentType(MediaType.APPLICATION_JSON).content(json))
//                 .andExpect(status().isBadRequest())
//                 .andExpect(jsonPath("$.error").exists());
//     }
// }
