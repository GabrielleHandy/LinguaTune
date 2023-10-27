package com.example.linguatune.controller;


import com.example.linguatune.model.User;
import com.example.linguatune.model.loginRequest.LoginRequest;
import com.example.linguatune.service.UserService;
import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/auth/users")
public class UserController {

@Autowired
private UserService userService;



    static HashMap<String, Object> result = new HashMap<>();

@PostMapping(path = "/login")
public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest){
    Optional<String> userOptional =userService.loginUser(loginRequest);
    if(userOptional.isPresent()){
        result.put("message", "Successfully logged in!");
        result.put("data", userOptional.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }else {
        result.put("message", "Invalid login");
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}

    @PostMapping(path = "/register")
    public ResponseEntity<?> registerUser(@RequestBody User user){
        Optional<User> userOptional = Optional.ofNullable(userService.createUser(user));
        if(userOptional.isPresent()){
            result.put("message", "Created successfully!");
            result.put("data", userOptional.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        }else {
            result.put("message", "User already exists");
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }
    }



@PutMapping(path = "/{id}")
public ResponseEntity<?> updateUserInfo(@RequestBody User user, @PathVariable (value = "id") Long id){
    Optional<User> userOptional = Optional.ofNullable(userService.updateUser(id, user));
    if (userOptional.isPresent()) {
        result.put("message", "success");
        result.put("data", userOptional.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    } else {
        result.put("message", "cannot find user with id " + id);
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }
}

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable (value = "id") Long id){
        Optional<User> userOptional = Optional.ofNullable(userService.deleteUser(id));
        if (userOptional.isPresent()) {
            result.put("message", "successfully deleted");
            result.put("data", userOptional.get());
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            result.put("message", "cannot delete user with id " + id);
            return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
        }
    }



@GetMapping(path = "/email/{email}")
    public ResponseEntity<?> findUserByEmail(@PathVariable(value="email") String email){
    Optional<User> userOptional = Optional.ofNullable(userService.findByEmailAddress(email));
    if(userOptional.isPresent()){
        result.put("message", "success");
        result.put("data", userOptional.get());
        return new ResponseEntity<>(result, HttpStatus.OK);
    } else {
        result.put("message", "cannot delete user with email " + email);
        return new ResponseEntity<>(result, HttpStatus.NOT_FOUND);
    }


}



}
