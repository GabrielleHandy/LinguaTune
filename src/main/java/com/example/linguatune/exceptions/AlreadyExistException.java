package com.example.linguatune.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyExistException extends RuntimeException {
    public AlreadyExistException(String message){
        super(message);
    }
}
