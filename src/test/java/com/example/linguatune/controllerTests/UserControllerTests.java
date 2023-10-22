package com.example.linguatune.controllerTests;

import com.example.linguatune.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(Userontroller.class)
public class UserControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    UserService userService;

    @Autowired
    ObjectMapper objectMapper;
}
