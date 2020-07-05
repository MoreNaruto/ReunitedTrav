package com.tmorris.reunitedtrav.controllers;

import com.tmorris.reunitedtrav.controllers.jsonbodies.TravelerSignUpForm;
import com.tmorris.reunitedtrav.services.TravelerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/traveler")
public class TravelerController {
    private final TravelerService travelerService;

    @Autowired
    public TravelerController(TravelerService travelerService) {
        this.travelerService = travelerService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody TravelerSignUpForm signUpForm) {
        travelerService.signUp(signUpForm);
        return ResponseEntity.accepted().build();
    }
}

