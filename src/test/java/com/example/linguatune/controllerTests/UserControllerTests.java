package com.example.linguatune.controllerTests;

import com.example.linguatune.controller.UserController;
import com.example.linguatune.model.User;
import com.example.linguatune.security.*;
import com.example.linguatune.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Date;
import java.util.Optional;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.example")
//@WebMvcTest(UserController.class)
//@ContextConfiguration(classes = SecurityConfiguration.class)
public class UserControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    private UserService userService;

    @MockBean
    MyUserDetailsService myUserDetailsService;


    @Autowired
    ObjectMapper objectMapper;

    private User testUser_1 = new User(1L, "test1", "test1@", null, null);
    private User testUser_2 = new User(2L, "test2", "test2@", null, null);
    private User testUser_3 = new User(3L, "test3", "test3@", null, null);

    private String jwtKey;


    @Test
    public void createUser_success() throws Exception {


        when(userService.createUser(any(User.class))).thenReturn(testUser_2);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser_2)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Created successfully!"))
                .andExpect(jsonPath("$.data.userName").value(testUser_2.getUserName()))
                .andDo(print());

    }

    @Test
    public void createUser_fail() throws Exception {


        when(userService.createUser(any(User.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/auth/users/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser_2)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("User already exists"))
                .andDo(print());

    }


    @Test
    @WithMockUser(username = "gabby@ga")
    public void updateUser_success() throws Exception {
        MyUserDetails userDetails = setup();
        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myUserDetailsService.loadUserByUsername("gabby@ga")).thenReturn(userDetails);

        User updateUser = new User();
        updateUser.setUserName("woop");

        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(updateUser);

        mockMvc.perform(MockMvcRequestBuilders.put("/auth/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser_1))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("success"))
                .andExpect(jsonPath("$.data.userName").value(updateUser.getUserName()))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void updateUser_fail() throws Exception {
        MyUserDetails userDetails = setup();
        when(myUserDetailsService.loadUserByUsername("gabby@ga")).thenReturn(userDetails);

        User updateUser = new User();
        updateUser.setUserName("woop");

        when(userService.updateUser(anyLong(), any(User.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/auth/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(testUser_1))
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot find user with id 1"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void deleteUser_success() throws Exception {
        MyUserDetails userDetails = setup();

        when(myUserDetailsService.loadUserByUsername("gabby@ga")).thenReturn(userDetails);



        when(userService.deleteUser(anyLong())).thenReturn(testUser_1);

        mockMvc.perform(MockMvcRequestBuilders.delete("/auth/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("successfully deleted"))
                .andExpect(jsonPath("$.data.userName").value(testUser_1.getUserName()))
                .andDo(print());

    }
    @Test
    @WithMockUser(username = "gabby@ga")
    public void deleteUser_fail() throws Exception {
        MyUserDetails userDetails = setup();

        when(myUserDetailsService.loadUserByUsername("gabby@ga")).thenReturn(userDetails);



        when(userService.deleteUser(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/auth/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot delete user with id 1"))

                .andDo(print());

    }



    private String generateJwtToken() {

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject("suresh@ga.com")
                .setExpiration(new Date((new Date()).getTime() + 86400000))
                .signWith(SignatureAlgorithm.HS256, "C6UlILsE6GJwNqwCTkkvJj9O653yJUoteWMLfYyrc3vaGrrTOrJFAUD1wEBnnposzcQl");
        return jwtBuilder.compact();
    }

    private MyUserDetails setup() {


        User testUser = new User(1L, "GabbyDev", "gabby@ga","", null);
        testUser.setPassword("password");

        return new MyUserDetails(testUser);
    }
}
