package com.example.linguatune.controllerTests;

import com.example.linguatune.model.*;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.security.MyUserDetailsService;
import com.example.linguatune.service.LanguageService;
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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.example")
public class LanguageControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    private LanguageService languageService;

    @MockBean
    MyUserDetailsService myUserDetailsService;


    @Autowired
    ObjectMapper objectMapper;

    Language English = new Language();
        
   

    Language Spanish = new Language();
        
    

    private List<Language> languageList = new ArrayList<>();

    private String jwtKey;

    @BeforeEach
    public void setUp(){
        English.setLanguageCode("eng");
        English.setName("English");


        Spanish.setLanguageCode("spa");
        Spanish.setName("Spanish");

        languageList.add(English);
        languageList.add(Spanish);
        MyUserDetails userDetails = setup();

        when(myUserDetailsService.loadUserByUsername("gabby@ga")).thenReturn(userDetails);



    }


    @Test
    @WithMockUser(username = "gabby@ga")
    public void getLanguageById_success() throws Exception {

        when(languageService.getLanguageById(anyLong())).thenReturn(English);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/languages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.name").value(English.getName()))
                .andDo(print());

    }
    @Test
    @WithMockUser(username = "gabby@ga")
    public void getLanguageById_fail() throws Exception {

        when(languageService.getLanguageById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/languages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Language with id 1 not found"))
                .andDo(print());

    }
    @Test
    @WithMockUser(username = "gabby@ga")
    public void getLAllLanguages() throws Exception {

        when(languageService.getLanguages()).thenReturn(languageList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/languages/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data[0].name").value(English.getName()))
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
