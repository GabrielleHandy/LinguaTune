package com.example.linguatune.service;

import com.example.linguatune.exceptions.InformationNotFoundException;
import com.example.linguatune.model.User;
import com.example.linguatune.model.loginRequest.LoginRequest;
import com.example.linguatune.repository.LanguageRepository;
import com.example.linguatune.repository.UserRepository;
import com.example.linguatune.security.JWTUtils;
import com.example.linguatune.security.MyUserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    private LanguageRepository languageRepository;

    private final JWTUtils jwtUtils;
    private  AuthenticationManager authenticationManager;
    private static User loggedinUser;


    private PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, LanguageRepository languageRepository, JWTUtils jwtUtils, @Lazy AuthenticationManager authenticationManager, @Lazy PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.languageRepository = languageRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Set a test logged-in user (used for testing purposes).
     *
     * @param user The user to be set as the logged-in user.
     */
    public void setTestLoggedInUser(User user) {
        loggedinUser = user;
    }

    /**
     * Set the logged-in user based on the current security context.
     */
    public void setLoggedInUser() {
        MyUserDetails userDetails = (MyUserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        loggedinUser = userDetails.getUser();
    }

    /**
     * Find a user by their ID.
     *
     * @param id The ID of the user to retrieve.
     * @return The user with the specified ID.
     * @throws InformationNotFoundException if no user is found with the given ID.
     */
    public User findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get();
        }
        throw new InformationNotFoundException("User does not exist");
    }

    /**
     * Find a user by their email address.
     *
     * @param email The email address of the user to retrieve.
     * @return The user with the specified email address.
     */
    public User findByEmailAddress(String email) {
        return userRepository.findByEmailAddress(email);
    }

    /**
     * Create a new user.
     *
     * @param user The user object to be created.
     * @return The created user.
     */
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    /**
     * Update a user's information.
     *
     * @param userId       The ID of the user to be updated.
     * @param updatedUser  The updated user object.
     * @return The updated user.
     * @throws InformationNotFoundException if the user to update is not assigned to the logged-in user.
     */
    public User updateUser(Long userId, User updatedUser) {
        setLoggedInUser();
        if (userId == loggedinUser.getId()) {
            if (updatedUser.getUserName() != null) {
                loggedinUser.setUserName(updatedUser.getUserName());
            }
            if (updatedUser.getPassword() != null) {
                loggedinUser.setPassword(passwordEncoder.encode(updatedUser.getPassword()));
            }
            return userRepository.save(loggedinUser);
        } else {
            throw new InformationNotFoundException("Can't update a user that isn't assigned to you");
        }
    }

    /**
     * Delete a user.
     *
     * @param id The ID of the user to be deleted.
     * @return The deleted user.
     * @throws InformationNotFoundException if the user to delete is not assigned to the logged-in user.
     */
    public User deleteUser(Long id) {
        setLoggedInUser();
        if (id == loggedinUser.getId()) {
            userRepository.deleteById(id);
            return loggedinUser;
        } else {
            throw new InformationNotFoundException("Can't delete a user that isn't assigned to you");
        }
    }

    /**
     * Attempts to log in a user using the provided login credentials.
     *
     * @param loginRequest The login request containing the user's email address and password.
     * @return true if the login is successful, false if login fails due to invalid credentials.
     */
    public Optional<String> loginUser(LoginRequest loginRequest){

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginRequest.getEmailAddress(), loginRequest.getPassword());
        try{
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDetails myUserDetails =  (MyUserDetails) authentication.getPrincipal();

            return Optional.of(jwtUtils.generateJwtToken(myUserDetails));
        }catch (Exception e){
            return Optional.empty();
        }
    }


}
