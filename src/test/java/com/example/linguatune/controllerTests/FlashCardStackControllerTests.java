package com.example.linguatune.controllerTests;

import com.example.linguatune.model.FlashCardStack;
import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.security.MyUserDetailsService;
import com.example.linguatune.service.FlashCardStackService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Date;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.example")
public class FlashCardStackControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    private FlashCardStackService flashCardStackService;

    @MockBean
    MyUserDetailsService myUserDetailsService;


    @Autowired
    ObjectMapper objectMapper;

    private FlashCardStack testFlashCardStack_1 = new FlashCardStack(1L, "test", new StudyPage(), null);


    private String jwtKey;

    @BeforeEach
    public void setUp(){

        MyUserDetails userDetails = setup();

        when(myUserDetailsService.loadUserByUsername("gabby@ga")).thenReturn(userDetails);


    }


    @Test
    @WithMockUser(username = "gabby@ga")
    public void createFlashCardStack_success() throws Exception {


        when(flashCardStackService.createStack(any(FlashCardStack.class), anyLong())).thenReturn(testFlashCardStack_1);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/stacks/create/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken())
                        .content(objectMapper.writeValueAsString(testFlashCardStack_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Created successfully!"))
                .andExpect(jsonPath("$.data.title").value(testFlashCardStack_1.getTitle()))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void createFlashCardStack_fail() throws Exception {


        when(flashCardStackService.createStack(any(FlashCardStack.class), anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/stacks/create/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken())
                        .content(objectMapper.writeValueAsString(testFlashCardStack_1)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Error creating Stack"))
                .andDo(print());

    }



    @Test
    @WithMockUser(username = "gabby@ga")
    public void getFlashCardStackById_success() throws Exception {

        when(flashCardStackService.findById(anyLong())).thenReturn(testFlashCardStack_1);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/stacks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.title").value(testFlashCardStack_1.getTitle()))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void getFlashCardStackById_fail() throws Exception {


        when(flashCardStackService.findById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/stacks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Stack with id 1 not found"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void deleteFlashCardStackById_success() throws Exception {



        when(flashCardStackService.deleteStack(anyLong())).thenReturn(testFlashCardStack_1);


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stacks/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("successfully deleted"))
                .andExpect(jsonPath("$.data.title").value(testFlashCardStack_1.getTitle()))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void deleteFlashCardStackById_fail() throws Exception {


        when(flashCardStackService.deleteStack(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/stacks/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot delete stack with id 1"))
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
