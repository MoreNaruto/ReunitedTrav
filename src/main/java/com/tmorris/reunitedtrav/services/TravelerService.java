package com.tmorris.reunitedtrav.services;

import com.tmorris.reunitedtrav.controllers.requestbodies.TravelerSignUpForm;
import com.tmorris.reunitedtrav.models.Traveler;
import com.tmorris.reunitedtrav.models.User;
import com.tmorris.reunitedtrav.repositories.TravelerRepository;
import com.tmorris.reunitedtrav.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TravelerService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TravelerRepository travelerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Transactional(rollbackOn = Exception.class)
    public void signUp(TravelerSignUpForm signUpForm) {
        User user = User.builder()
                .username(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();

        userRepository.save(user);

        Traveler traveler = Traveler.builder()
                .user(user)
                .profilePicture(signUpForm.getProfilePicture())
                .homeState(signUpForm.getHomeState())
                .homeCity(signUpForm.getHomeCity())
                .firstName(signUpForm.getFirstName())
                .lastName(signUpForm.getLastName())
                .phoneNumber(signUpForm.getPhoneNumber())
                .email(signUpForm.getEmail())
                .build();

        travelerRepository.save(traveler);
    }
}
