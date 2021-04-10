package com.tmorris.reunitedtrav.services;

import com.tmorris.reunitedtrav.controllers.jsonbodies.AgentSignUpForm;
import com.tmorris.reunitedtrav.controllers.jsonbodies.TravelerSignUpForm;
import com.tmorris.reunitedtrav.models.Account;
import com.tmorris.reunitedtrav.models.Agent;
import com.tmorris.reunitedtrav.models.Role;
import com.tmorris.reunitedtrav.models.Traveler;
import com.tmorris.reunitedtrav.repositories.AccountRepository;
import com.tmorris.reunitedtrav.repositories.AgentRepository;
import com.tmorris.reunitedtrav.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;

@Service
public class AgentService {
    private AgentRepository agentRepository;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AgentService(
            AgentRepository agentRepository,
            RoleRepository roleRepository,
            AccountRepository accountRepository,
            PasswordEncoder passwordEncoder) {
        this.agentRepository = agentRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.accountRepository = accountRepository;
    }

    @Transactional(rollbackOn = Exception.class)
    public void signUp(AgentSignUpForm signUpForm) {
        Account account = Account.builder()
                .username(signUpForm.getUsername())
                .password(passwordEncoder.encode(signUpForm.getPassword()))
                .isEnabled(true)
                .roles(Set.of(roleRepository.findRoleByName(Role.ROLE_AGENT)))
                .build();

        accountRepository.save(account);

        Agent agent = Agent.builder()
                .account(account)
                .profilePicture(signUpForm.getProfilePicture())
                .firstName(signUpForm.getFirstName())
                .lastName(signUpForm.getLastName())
                .phoneNumber(signUpForm.getPhoneNumber())
                .email(signUpForm.getEmail())
                .build();

        agentRepository.save(agent);
    }
}
