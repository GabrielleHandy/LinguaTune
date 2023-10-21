package com.example.linguatune.service;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.User;
import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.repository.UserRepository;
import com.example.linguatune.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    @Autowired
    private LanguageRepository languageRepository;
    @Autowired
    private  AuthenticationManager authenticationManager;
    private static User loggedinUser;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public void setTestLoggedInUser(User user){
        loggedinUser = user;

    }

    public void setLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggedinUser = userDetails.getUser();
        
    }

    public User findById(Long id){
        Optional<User> optionalUser = userRepository.findById(id);
        if(optionalUser.isPresent()){
            return optionalUser.get();
        }
        throw new InformationNotFoundException("User does not exist");
    }

    public User findByEmailAddress(String email){
        return userRepository.findByEmailAddress(email);
    }

    public User createUser(User user) {
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setNativeLanguage(languageRepository.findById(1L).get());

        return userRepository.save(user);
    }

    public User updateUser(Long userId, User updatedUser) {
        if(userId == loggedinUser.getId()){
        
            
            
                if(updatedUser.getUserName() != null){
                    loggedinUser.setUserName(updatedUser.getUserName());

                }
                if(updatedUser.getPassword() != null){
                    loggedinUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));

                }
            
           

            return userRepository.save(loggedinUser);
        } else {

           throw new InformationNotFoundException("Can't updated user that isn't assigned to you");
        }
    }


}
