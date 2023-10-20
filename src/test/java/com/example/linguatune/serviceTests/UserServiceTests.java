package com.example.linguatune.serviceTests;

import com.example.linguatune.repository.UserRepository;
import com.example.linguatune.service.UserService;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {

    @Mock
    UserService userService;
    @Mock
    UserRepository userRepository;

}
