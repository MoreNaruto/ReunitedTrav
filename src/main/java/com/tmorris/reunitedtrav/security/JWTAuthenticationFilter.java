package com.tmorris.reunitedtrav.security;

import com.google.gson.Gson;
import com.tmorris.reunitedtrav.models.Account;
import com.tmorris.reunitedtrav.security.util.JwtTokenUtil;
import com.tmorris.reunitedtrav.services.JpaAccountUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.tmorris.reunitedtrav.security.util.SecurityConstants.HEADER_STRING;
import static com.tmorris.reunitedtrav.security.util.SecurityConstants.TOKEN_PREFIX;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JpaAccountUserDetailsService userDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private AuthenticationManager authenticationManager;

    @Autowired
    public JWTAuthenticationFilter(
            JpaAccountUserDetailsService userDetailsService,
            JwtTokenUtil jwtTokenUtil,
            AuthenticationManager authenticationManager
    ) {
        this.userDetailsService = userDetailsService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Account credentials = new Gson().fromJson(String.valueOf(request.getInputStream()), Account.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
        String token = jwtTokenUtil.generateToken(userDetailsService.loadUserByUsername(((User) authResult.getPrincipal()).getUsername()));

        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }
}
