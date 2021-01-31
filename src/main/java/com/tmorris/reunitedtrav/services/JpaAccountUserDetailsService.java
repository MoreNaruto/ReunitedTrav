package com.tmorris.reunitedtrav.services;

import com.tmorris.reunitedtrav.models.Account;
import com.tmorris.reunitedtrav.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class JpaAccountUserDetailsService implements UserDetailsService {

    private final AccountRepository repository;

    @Autowired
    public JpaAccountUserDetailsService(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        Account account = this.repository.findAccountByUsername(name);
        if (account == null) {
            throw new UsernameNotFoundException("Could not find account");
        }
        return new JpaAccountUserDetails(account);
    }

}
