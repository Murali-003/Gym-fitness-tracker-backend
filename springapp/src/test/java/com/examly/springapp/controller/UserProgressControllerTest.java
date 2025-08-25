// package com.examly.springapp.controller;

// import com.examly.springapp.model.User;
// import com.examly.springapp.model.WorkoutPlan;
// import com.examly.springapp.model.UserPlanProgress;
// import com.examly.springapp.model.enums.Role;
// import com.examly.springapp.model.enums.Difficulty;
// import com.examly.springapp.service.UserPlanProgressService;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.junit.jupiter.api.Test;
// import org.mockito.Mockito;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.http.MediaType;
// import org.springframework.test.web.servlet.MockMvc;
// import java.time.LocalDate;
// import java.util.Arrays;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
// import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

// @WebMvcTest(com.examly.springapp.controller.UserProgressController.class)
// public class UserProgressControllerTest {
//     @Autowired
//     private MockMvc mockMvc;
//     @MockBean
//     private UserPlanProgressService userPlanProgressService;
//     @Autowired
//     private ObjectMapper objectMapper;

//     @Test
//     public void controller_testGetUserProgressById() throws Exception {
//         User u = User.builder().userId(1L).username("alice").email("a@b.com").role(Role.MEMBER).build();
//         WorkoutPlan w = WorkoutPlan.builder().planId(1L).title("Plan").difficulty(Difficulty.BEGINNER).createdBy(u).build();
//         UserPlanProgress progress = UserPlanProgress.builder().progressId(1L).user(u).workoutPlan(w).assignedDate(LocalDate.now()).completionPercentage(20).lastUpdated(LocalDate.now()).build();
//         Mockito.when(userPlanProgressService.getProgressByUser(1L)).thenReturn(Arrays.asList(progress));
//         mockMvc.perform(get("/api/users/1/progress")).andExpect(status().isOk()).andExpect(jsonPath("$[0].completionPercentage").value(20));
//     }

//     @Test
//     public void controller_testUpdateUserProgress() throws Exception {
//         UserPlanProgress upd = UserPlanProgress.builder().progressId(1L).completionPercentage(75).lastUpdated(LocalDate.of(2023,6,20)).build();
//         Mockito.when(userPlanProgressService.updateProgress(Mockito.anyLong(), Mockito.anyLong(), Mockito.anyInt(), Mockito.any(LocalDate.class))).thenReturn(upd);
//         String body = "{\"completionPercentage\":75, \"lastUpdated\":\"2023-06-20\"}";
//         mockMvc.perform(put("/api/users/1/progress/1").contentType(MediaType.APPLICATION_JSON).content(body))
//                 .andExpect(status().isOk()).andExpect(jsonPath("$.completionPercentage").value(75));
//     }

//     @Test
//     public void exception_testUpdateUserProgressInvalidPercent() throws Exception {
//         String body = "{\"completionPercentage\":120, \"lastUpdated\":\"2023-06-20\"}";
//         mockMvc.perform(put("/api/users/1/progress/1").contentType(MediaType.APPLICATION_JSON).content(body))
//                 .andExpect(status().isBadRequest()).andExpect(jsonPath("$.error").exists());
//     }
// }
