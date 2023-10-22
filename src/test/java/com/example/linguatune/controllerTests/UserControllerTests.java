package com.example.linguatune.controllerTests;

import com.example.linguatune.controller.UserController;
import com.example.linguatune.model.User;
import com.example.linguatune.security.SecurityConfiguration;
import com.example.linguatune.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(UserController.class)
@ContextConfiguration(classes = SecurityConfiguration.class)
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserService userService;



    @Autowired
    WebApplicationContext context;

    @Autowired
    ObjectMapper objectMapper;

    private User testUser_1 = new User(1L, "test1", "test1@", null, null);
    private User testUser_2 = new User(2L, "test2", "test2@", null, null);
    private User testUser_3 = new User(3L, "test3", "test3@", null, null);


    @BeforeEach
    public void setUp(){
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();




    }


    @Test
    @WithMockUser(username = "test2@")
    public void getUser_success() throws Exception {

        when(userService.findById(anyLong())).thenReturn(testUser_1);
        mockMvc.perform(get("/auth/users/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.id").value(testUser_1.getId()))
                .andExpect(jsonPath("$.data.userName").value(testUser_1.getUserName()))
                .andExpect(jsonPath("$.data.emailAddress").value(testUser_1.getEmailAddress()))
                .andExpect(jsonPath("$.message").value("success"))
                .andDo(print());
    }
}
