package com.asianpaints.apse.service_engineer.service;

import com.asianpaints.apse.service_engineer.domain.entity.Token;
import com.asianpaints.apse.service_engineer.jwt.JwtUtils;
import com.asianpaints.apse.service_engineer.repository.TokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Service
@AllArgsConstructor
public class LogoutService implements LogoutHandler {
    private final JwtUtils jwtUtils;
    private final TokenRepository tokenRepository;

    @Override
    public void logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Authentication authentication) {

        String jwt = jwtUtils.getJwtFromHeader(request);
        if(jwt == null){
            return;
        }
        Token storedToken = tokenRepository.findByToken(jwt)
                .orElse(null);
        if(storedToken != null){
            storedToken.setExpired(true);
            storedToken.setRevoked(true);
            tokenRepository.save(storedToken);
        }


    }
}
