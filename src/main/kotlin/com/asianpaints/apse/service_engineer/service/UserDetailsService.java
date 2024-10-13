package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.repository.ApUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private ApUserRepository apUserRepository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApUser user = apUserRepository.findByEmail(email);
        if(user==null){
            return new org.springframework.security.core.userdetails.User("", "",
                    new ArrayList<>());
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), "",
                new ArrayList<>());
    }
}