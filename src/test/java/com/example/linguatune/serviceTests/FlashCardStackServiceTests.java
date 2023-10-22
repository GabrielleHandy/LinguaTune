package com.example.linguatune.serviceTests;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.FlashCardStack;
import com.example.linguatune.model.Language;
import com.example.linguatune.model.StudyPage;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.FlashCardStackRepository;
import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.repository.UserRepository;
import com.example.linguatune.security.MyUserDetails;
import com.example.linguatune.service.FlashCardStackService;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FlashCardStackServiceTests {


    @Mock
    FlashCardStackRepository flashCardStackRepository;



    @InjectMocks
    FlashCardStackService flashCardStackService;







    private static User user;
    private Language eng;
    private Language spa;
    private StudyPage studyPage;
    private FlashCardStack updated;
    private FlashCardStack flashCardStack;

    @BeforeEach
    public void setUp(){




        eng = new Language();
        eng.setName("English");
        eng.setLanguageCode("en");
        eng.setId(1L);


        spa = new Language();
        spa.setId(2L);
        spa.setName("Spanish");
        user = new User(1L, "LanguageLover", "test@test.com", eng, "111", new ArrayList<StudyPage>() );
        studyPage = new StudyPage(1L, user, spa, null, null);
        flashCardStack = new FlashCardStack(2L, "pop", studyPage, new ArrayList<>());
        updated = new FlashCardStack();
        updated.setTitle("Bloop");



    }

    @Test
    public void testGetFlashCardStackById(){
        when(flashCardStackRepository.findById(anyLong())).thenReturn(Optional.ofNullable(flashCardStack));
        assertEquals(flashCardStackService.findById(1L).getId(), flashCardStack.getId());
    }

    @Test
    public void testGetUserByIdFail(){

        when(flashCardStackRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(InformationNotFoundException.class, () -> {

            flashCardStackService.findById(2L);

        });
    }

    
}
