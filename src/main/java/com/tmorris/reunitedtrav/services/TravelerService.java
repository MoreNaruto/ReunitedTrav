package com.tmorris.reunitedtrav.services;

import com.tmorris.reunitedtrav.controllers.jsonbodies.TravelerSignUpForm;
import com.tmorris.reunitedtrav.models.Account;
import com.tmorris.reunitedtrav.models.Traveler;
import com.tmorris.reunitedtrav.repositories.AccountRepository;
import com.tmorris.reunitedtrav.repositories.TravelerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TravelerService {
    private final AccountRepository accountRepository;
    private final TravelerRepository travelerRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TravelerService(AccountRepository accountRepository, TravelerRepository travelerRepository, PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.travelerRepository = travelerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackOn = Exception.class)
    public void signUp(TravelerSignUpForm signUpForm) {
        Account account = Account.builder()
                .username(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .build();

        accountRepository.save(account);

        Traveler traveler = Traveler.builder()
                .account(account)
                .profilePicture(signUpForm.getProfilePicture())
                .homeState(signUpForm.getHomeState())
                .homeCity(signUpForm.getHomeCity())
                .firstName(signUpForm.getFirstName())
                .lastName(signUpForm.getLastName())
                .phoneNumber(signUpForm.getPhoneNumber())
                .email(signUpForm.getEmail())
                .birthday(signUpForm.getBirthday())
                .build();

        travelerRepository.save(traveler);
    }
}
