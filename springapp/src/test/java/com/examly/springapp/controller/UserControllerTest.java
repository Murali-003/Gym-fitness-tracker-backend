// package com.examly.springapp.controller;

// import com.examly.springapp.model.User;
// import com.examly.springapp.model.enums.Role;
// import com.examly.springapp.service.UserService;
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
// import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// @WebMvcTest(UserController.class)
// public class UserControllerTest {
//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private UserService userService;

//     @Test
//     public void controller_testGetAllUsers() throws Exception {
//         User u1 = User.builder().userId(1L).username("alice").email("a@b.com").role(Role.MEMBER).joinDate(LocalDate.now()).build();
//         User u2 = User.builder().userId(2L).username("bob").email("b@b.com").role(Role.TRAINER).joinDate(LocalDate.now()).build();
//         Mockito.when(userService.getAllUsers()).thenReturn(Arrays.asList(u1, u2));
//         mockMvc.perform(get("/api/users")).andExpect(status().isOk()).andExpect(jsonPath("$[0].username").value("alice"));
//     }
// }
