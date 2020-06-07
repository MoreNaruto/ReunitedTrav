package com.tmorris.reunitedtrav.controllers;

import com.tmorris.reunitedtrav.controllers.requestbodies.AuthenticationReponse;
import com.tmorris.reunitedtrav.controllers.requestbodies.AuthenticationRequest;
import com.tmorris.reunitedtrav.controllers.requestbodies.TravelerSignUpForm;
import com.tmorris.reunitedtrav.services.TravelerService;
import com.tmorris.reunitedtrav.services.TravelerUserDetailsService;
import com.tmorris.reunitedtrav.utils.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/traveler")
public class TravelerController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TravelerUserDetailsService userDetailsService;

    @Autowired
    private TravelerService travelerService;

    @Autowired
    private JWTUtil jwtUtil;

    @PostMapping("/sign-up")
    public ResponseEntity<?> signUp(@RequestBody TravelerSignUpForm signUpForm) {
        travelerService.signUp(signUpForm);
        return ResponseEntity.accepted().build();
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(
            @RequestBody AuthenticationRequest authenticationRequest
    ) throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    )
            );
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(
                authenticationRequest.getUsername());

        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationReponse(jwt));
    }
}

