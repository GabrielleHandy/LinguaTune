package com.example.linguatune.serviceTests;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.Language;
import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.repository.UserRepository;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UserServiceTests {


    @Mock
    UserRepository userRepository;



    @InjectMocks
    UserService userServiceMock;

    @Mock
    MyUserDetails myUserDetails;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    LanguageRepository languageRepository;

    @Mock
    SecurityContextHolder securityContextHolder;






    public  UsernamePasswordAuthenticationToken authentication;
    private static User user;
    private Language eng;


    @BeforeEach
    public void setUp(){
        MockitoAnnotations.openMocks(this);
        userServiceMock.setUserRepository(userRepository);
        



        eng = new Language();
        eng.setName("English");
        eng.setLanguageCode("en");
        eng.setId(1L);


        user = new User(1L, "LanguageLover", "test@test.com", eng, "111", new ArrayList<StudyPage>() );
        userServiceMock.setTestLoggedInUser(user);
    }

    @Test
    public void testGetUserById(){
        when(userServiceMock.findById(anyLong())).thenReturn(user);
        assertEquals(userServiceMock.findById(1L).getId(), user.getId());
    }

    @Test
    public void testGetUserByIdFail(){

        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InformationNotFoundException.class, () -> {

            userServiceMock.findById(2L);

        });
    }

    @Test
    public void testFindByEmailAddress(){
        when(userRepository.findByEmailAddress(anyString())).thenReturn(user);
        assertEquals(userServiceMock.findByEmailAddress("test").getId(), user.getId());
    }

    @Test
    public void testCreateUser(){
    when(languageRepository.findById(1L)).thenReturn(Optional.ofNullable(eng));
    when(userRepository.save(any(User.class))).thenReturn(user);
    User result = userServiceMock.createUser(user);
    assertEquals(result.getEmailAddress(), user.getEmailAddress());

    }
    @Test
    public void testUpdateUser(){
        
        User updated = new User();
        updated.setUserName("Bloop");

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(updated);
        User result = userServiceMock.updateUser(1L, updated);

        assert(result.getUserName().equals(user.getUserName()));

    }

    @Test
    public void testUpdateUserFail(){
        
        User updated = new User();
        
        

       
        assertThrows(InformationNotFoundException.class, () -> {

            userServiceMock.updateUser(3L, updated);


        });
        

    }
    
    @Test
    public void testDeleteUser(){
        
        User result = userServiceMock.deleteUser(1L);

        assert(result.getUserName().equals(user.getUserName()));

    }
    
}
