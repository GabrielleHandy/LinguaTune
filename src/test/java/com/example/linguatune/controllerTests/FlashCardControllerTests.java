package com.example.linguatune.controllerTests;

import com.example.linguatune.model.FlashCard;
import com.example.linguatune.model.FlashCardStack;
import com.example.linguatune.model.Song;
import com.example.linguatune.model.User;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.security.MyUserDetailsService;
import com.example.linguatune.service.FlashCardService;
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
public class FlashCardControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    private FlashCardService flashCardService;

    @MockBean
    MyUserDetailsService myUserDetailsService;


    @Autowired
    ObjectMapper objectMapper;

    private FlashCard testFlashCard_1 = new FlashCard(1L, new FlashCardStack(),new Song(), "test", "translate");


    private String jwtKey;

    @BeforeEach
    public void setUp(){

        MyUserDetails userDetails = setup();

        when(myUserDetailsService.loadUserByUsername("gabby@ga")).thenReturn(userDetails);


    }


    @Test
    @WithMockUser(username = "gabby@ga")
    public void createFlashCard_success() throws Exception {


        when(flashCardService.createFlashCard(anyLong(), any(FlashCard.class), anyLong())).thenReturn(testFlashCard_1);


        mockMvc.perform(MockMvcRequestBuilders.post("/api/flashcards/create/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken())
                        .content(objectMapper.writeValueAsString(testFlashCard_1)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Created successfully!"))
                .andExpect(jsonPath("$.data.originalText").value(testFlashCard_1.getOriginalText()))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void createFlashCard_fail() throws Exception {


        when(flashCardService.createFlashCard(anyLong(), any(FlashCard.class), anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/flashcards/create/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken())
                        .content(objectMapper.writeValueAsString(testFlashCard_1)))
                .andExpect(status().isConflict())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Error creating card"))
                .andDo(print());

    }



    @Test
    @WithMockUser(username = "gabby@ga")
    public void getFlashCardById_success() throws Exception {

        when(flashCardService.findById(anyLong())).thenReturn(testFlashCard_1);


        mockMvc.perform(MockMvcRequestBuilders.get("/api/flashcards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.originalText").value(testFlashCard_1.getOriginalText()))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void getFlashCardById_fail() throws Exception {


        when(flashCardService.findById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/flashcards/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Flashcard with id 1 not found"))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void deleteFlashCardById_success() throws Exception {



        when(flashCardService.deleteFlashCard(anyLong())).thenReturn(testFlashCard_1);


        mockMvc.perform(MockMvcRequestBuilders.delete("/api/flashcards/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("successfully deleted"))
                .andExpect(jsonPath("$.data.originalText").value(testFlashCard_1.getOriginalText()))
                .andDo(print());

    }

    @Test
    @WithMockUser(username = "gabby@ga")
    public void deleteFlashCardById_fail() throws Exception {


        when(flashCardService.deleteFlashCard(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/flashcards/delete/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("cannot delete flashcard with id 1"))
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
