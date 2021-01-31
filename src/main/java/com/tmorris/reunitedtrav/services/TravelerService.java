package com.tmorris.reunitedtrav.services;

import com.tmorris.reunitedtrav.controllers.jsonbodies.TravelerSignUpForm;
import com.tmorris.reunitedtrav.models.Account;
import com.tmorris.reunitedtrav.models.Role;
import com.tmorris.reunitedtrav.models.Traveler;
import com.tmorris.reunitedtrav.repositories.AccountRepository;
import com.tmorris.reunitedtrav.repositories.RoleRepository;
import com.tmorris.reunitedtrav.repositories.TravelerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class TravelerService {
    private final AccountRepository accountRepository;
    private final TravelerRepository travelerRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public TravelerService(
            AccountRepository accountRepository,
            TravelerRepository travelerRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder) {
        this.accountRepository = accountRepository;
        this.travelerRepository = travelerRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(rollbackOn = Exception.class)
    public void signUp(TravelerSignUpForm signUpForm) {
        Account account = Account.builder()
                .username(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .isEnabled(true)
                .roles(Set.of(roleRepository.findRoleByName(Role.ROLE_TRAVELER)))
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
