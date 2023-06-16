package com.wvaviator.greenkeep.security;

import com.wvaviator.greenkeep.user.CustomOidcUser;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        CustomOidcUser customOidcUser = (CustomOidcUser) authentication.getPrincipal();
        String jwt = jwtService.generateToken(customOidcUser);

        response.setHeader("Authorization", "Bearer " + jwt);
    }
}

