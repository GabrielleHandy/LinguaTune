package com.example.linguatune.controllerTests;

import com.example.linguatune.model.*;
import com.example.linguatune.model.Song;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.security.MyUserDetailsService;
import com.example.linguatune.service.SongService;
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
public class SongControllerTests {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    @MockBean
    private SongService SongService;

    @MockBean
    MyUserDetailsService myUserDetailsService;


    @Autowired
    ObjectMapper objectMapper;

    private Song testSong = new Song(2L, "test", "woop",new ArrayList<FlashCard>(), "1234","English", "link", "flfllfl", new ArrayList<Translation>());
    private Song testSong2 = new Song(3L, "testTest", "woop",new ArrayList<FlashCard>(), "1234","English", "link", "flfllfl", new ArrayList<Translation>());

    private List<Song> songList = new ArrayList<>();

    private String jwtKey;

    @BeforeEach
    public void setUp(){

        MyUserDetails userDetails = setup();

        when(myUserDetailsService.loadUserByUsername("gabby@ga")).thenReturn(userDetails);

        songList.add(testSong2);
        songList.add(testSong);

    }


    @Test
    @WithMockUser(username = "gabby@ga")
    public void getSongById_success() throws Exception {

        when(SongService.getSongById(anyLong())).thenReturn(testSong);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data.title").value(testSong.getTitle()))
                .andDo(print());

    }
    @Test
    @WithMockUser(username = "gabby@ga")
    public void getSongById_fail() throws Exception {

        when(SongService.getSongById(anyLong())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Song with id 1 not found"))
                .andDo(print());

    }
    @Test
    @WithMockUser(username = "gabby@ga")
    public void getSongByArtist_success() throws Exception {

        when(SongService.getSongsByArtist(anyString())).thenReturn(songList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/artist/Stromae")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Success"))
                .andExpect(jsonPath("$.data[0].title").value(testSong2.getTitle()))
                .andDo(print());

    }
    @Test
    @WithMockUser(username = "gabby@ga")
    public void getSongByArtist_fail() throws Exception {

        when(SongService.getSongsByArtist(anyString())).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/songs/artist/Poppy")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + generateJwtToken()))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message").value("Can't find any songs by artist Poppy"))
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
