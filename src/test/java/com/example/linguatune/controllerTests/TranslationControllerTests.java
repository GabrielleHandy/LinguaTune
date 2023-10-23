package com.example.linguatune.controllerTests;

import com.example.linguatune.model.Song;
import com.example.linguatune.model.Translation;
import com.example.linguatune.model.User;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.security.MyUserDetailsService;
import com.example.linguatune.service.TranslationService;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.example")
public class TranslationControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    private TranslationService translationService;

    @MockBean
    MyUserDetailsService myUserDetailsService;


    @Autowired
    ObjectMapper objectMapper;

    private Translation testTranslation = new Translation(1L, null, new Song(), "lines" );


    private String jwtKey;

    @BeforeEach
    public void setUser(){

        MyUserDetails userDetails = setup();
        // Mock the behavior of myDoctorDetailsService to load the user details
        when(myUserDetailsService.loadUserByUsername("gabby@ga")).thenReturn(userDetails);

    }


    @Test
    @WithMockUser(username = "gabby@ga")
    public void getTranslationById_success() throws Exception {

        when(translationService.getTranslation(anyLong())).thenReturn(testTranslation);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/translations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.lines").value(testTranslation.getLines()))
                .andDo(print());

    }
    @Test
    @WithMockUser(username = "gabby@ga")
    public void getTranslationById_fail() throws Exception {

        when(translationService.getTranslation(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/translations/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Translation with id 1 not found"))
                .andDo(print());

    }
    @Test
    @WithMockUser(username = "gabby@ga")
    public void getTranslationBySongId_success() throws Exception {

        when(translationService.getTranslationBySong(anyLong())).thenReturn(testTranslation);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/translations/song/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.lines").value(testTranslation.getLines()))
                .andDo(print());

    }
    @Test
    @WithMockUser(username = "gabby@ga")
    public void getTranslationBySongId_fail() throws Exception {

        when(translationService.getTranslation(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/translations/song/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Translation for song with id 1 not found"))
                .andDo(print());

    }







    private String generateJwtToken() {

        JwtBuilder jwtBuilder = Jwts.builder()
                .setIssuedAt(new Date())
                .setSubject("gabby@ga")
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
