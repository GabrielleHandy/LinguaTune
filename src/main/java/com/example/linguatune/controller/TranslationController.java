package com.example.linguatune.controller;

import com.example.linguatune.repository.TranslationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/translation")
public class TranslationController {

    @Autowired
    private TranslationRepository translationRepository;




}
