package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.ApUser;
import com.asianpaints.apse.service_engineer.domain.entity.UserType;
import com.asianpaints.apse.service_engineer.repository.ApUserRepository;
import com.asianpaints.apse.service_engineer.repository.UserTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserTypeRepository userTypeRepository;
    private final ApUserRepository apUserRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        ApUser user = apUserRepository.findByEmail(email);
        if(user==null){
            throw new UsernameNotFoundException(email);
        }
        UserType userType = userTypeRepository.findById(user.getUserType().getId()).orElse(null);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), "",
                Collections.singletonList(new SimpleGrantedAuthority(userType.getUserType())));
    }
}