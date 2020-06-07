package com.tmorris.reunitedtrav.services;

import com.tmorris.reunitedtrav.utils.UpdateBCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class TravelerUserDetailsService implements UserDetailsService {
    @Autowired
    UpdateBCrypt updateBCrypt;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
