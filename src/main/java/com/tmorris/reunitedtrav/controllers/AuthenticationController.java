package com.tmorris.reunitedtrav.controllers;

import com.tmorris.reunitedtrav.annotations.JsonRequestMapping;
import com.tmorris.reunitedtrav.controllers.jsonbodies.AuthenticationResponse;
import com.tmorris.reunitedtrav.controllers.jsonbodies.AuthenticationRequest;
import com.tmorris.reunitedtrav.controllers.jsonbodies.TokenValidityRequest;
import com.tmorris.reunitedtrav.security.util.JwtTokenUtil;
import com.tmorris.reunitedtrav.services.JpaAccountUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private JpaAccountUserDetailsService userDetailsService;

    @Autowired
    public AuthenticationController(
            AuthenticationManager authenticationManager,
            JwtTokenUtil jwtTokenUtil,
            JpaAccountUserDetailsService userDetailsService
    ) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userDetailsService = userDetailsService;
    }

    @JsonRequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<AuthenticationResponse> createAuthenticationToken(@RequestBody(required = false) AuthenticationRequest authenticationRequest) throws Exception {
        if (authenticationRequest == null) {
            return null;
        }
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()
                    ));

        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        String token = jwtTokenUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @JsonRequestMapping(value = "/token-validity", method = RequestMethod.GET)
    public ResponseEntity<Boolean> isTokenValid(@RequestBody(required = false) TokenValidityRequest tokenValidityRequest) throws Exception {
        if (tokenValidityRequest == null) {
            return null;
        }
        UserDetails userDetails = userDetailsService.loadUserByUsername(tokenValidityRequest.getUsername());
        Boolean isValid = jwtTokenUtil.validateToken(tokenValidityRequest.getToken(), userDetails);
        return ResponseEntity.ok(isValid);
    }
}
