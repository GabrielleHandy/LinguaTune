package com.example.linguatune.controllerTests;

import com.example.linguatune.controller.UserController;
import com.example.linguatune.model.User;
import com.example.linguatune.security.SecurityConfiguration;
import com.example.linguatune.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@Import(SecurityConfiguration.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;





    @Autowired
    ObjectMapper objectMapper;

    private User testUser_1;
    private User testUser_2;
    private User testUser_3;


    @BeforeEach
    public void setUp(){

        testUser_1 = new User(1L, "test1", "test1@", null, null, null);
        testUser_2 = new User(2L, "test2", "test2@", null, null, null);
        testUser_3 = new User(3L, "test3", "test3@", null, null, null);



    }


    @Test
    @WithMockUser(username = "test1@")
    public void getUser_success() throws Exception {
        when(userService.findById(testUser_1.getId())).thenReturn(testUser_1);
        mockMvc.perform(get("/auth/users/{id}", "2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(testUser_1.getId()))
                .andExpect(jsonPath("$.data.userName").value(testUser_1.getUserName()))
                .andExpect(jsonPath("$.data.emailAddress").value(testUser_1.getEmailAddress()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }
}
