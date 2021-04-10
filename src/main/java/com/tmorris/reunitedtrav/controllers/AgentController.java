package com.tmorris.reunitedtrav.controllers;

import com.tmorris.reunitedtrav.controllers.jsonbodies.AgentSignUpForm;
import com.tmorris.reunitedtrav.services.AgentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agents")
public class AgentController {
    private final AgentService agentService;

    @Autowired
    public AgentController(AgentService agentService) {
        this.agentService = agentService;
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody AgentSignUpForm signUpForm) {
        agentService.signUp(signUpForm);
        return ResponseEntity.accepted().build();
    }
}
