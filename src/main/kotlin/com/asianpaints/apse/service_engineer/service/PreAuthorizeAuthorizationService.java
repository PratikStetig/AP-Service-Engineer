package com.asianpaints.apse.service_engineer.service;

import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PreAuthorizeAuthorizationService {

    public AuthorizationDecision check(String... roles){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

        List<String> userRoles = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());
        userRoles.retainAll(Arrays.asList(roles));
        if(userRoles.size() > 0){
           return new AuthorizationDecision(true);
        }
        return new AuthorizationDecision(false);
    }
}
