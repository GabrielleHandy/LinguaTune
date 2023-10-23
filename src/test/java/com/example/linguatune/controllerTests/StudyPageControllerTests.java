package com.example.linguatune.controllerTests;

import com.example.linguatune.model.Language;
import com.example.linguatune.model.StudyPage;

import com.example.linguatune.model.User;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.security.MyUserDetailsService;
import com.example.linguatune.service.StudyPageService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.example")
public class StudyPageControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    private StudyPageService StudyPageService;

    @MockBean
    MyUserDetailsService myUserDetailsService;


    @Autowired
    ObjectMapper objectMapper;
    private User testUser_1 = new User(1L, "test1", "test1@", null, null);

    private StudyPage testStudyPage_1 = new StudyPage(1L, testUser_1, new Language(), null, null);


    private String jwtKey;

    @BeforeEach
    public void setUp(){

        MyUserDetails userDetails = setup();

        when(myUserDetailsService.loadUserByUsername("gabby@ga")).thenReturn(userDetails);


    }


    @Test
    @WithMockUser(username = "gabby@ga")
    public void createStudyPage_success() throws Exception {
        testStudyPage_1.getLanguage().setName("French");

        when(StudyPageService.createStudyPage(anyString())).thenReturn(testStudyPage_1);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/studypages/create/French/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Created successfully!"))
                .andExpect(jsonPath("$.data.language.name").value("French"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void createStudyPage_fail() throws Exception {


        when(StudyPageService.createStudyPage(anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/studypages/create/French/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("StudyPage already exists"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void createStudyPageSpanish_success() throws Exception {
        testStudyPage_1.getLanguage().setName("Spanish");

        when(StudyPageService.createStudyPage(anyString())).thenReturn(testStudyPage_1);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/studypages/create/Spanish/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Created successfully!"))
                .andExpect(jsonPath("$.data.language.name").value("Spanish"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void createStudyPageSpanish_fail() throws Exception {


        when(StudyPageService.createStudyPage(anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/studypages/create/Spanish/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("StudyPage already exists"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void getStudyPageById_success() throws Exception {

        testStudyPage_1.getLanguage().setName("Spanish");

        when(StudyPageService.findStudyPageById(anyLong())).thenReturn(testStudyPage_1);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/studypages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.language.name").value("Spanish"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void getStudyPageById_fail() throws Exception {


        when(StudyPageService.createStudyPage(anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/studypages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Study Page with id 1 not found"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void deleteStudyPageById_success() throws Exception {

        testStudyPage_1.getLanguage().setName("Spanish");

        when(StudyPageService.deleteStudyPage(anyLong())).thenReturn(testStudyPage_1);


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/studypages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("successfully deleted"))
                .andExpect(jsonPath("$.data.language.name").value("Spanish"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void deleteStudyPageById_fail() throws Exception {


        when(StudyPageService.deleteStudyPage(any())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/studypages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot delete study page with id 1"))
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
