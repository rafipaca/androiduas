package com.rafirs.projectutsppk.auth;



import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author RafiRS
 */

@Component
public class AuthEntryPointJwt implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(AuthEntryPointJwt.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        logger.error("Unauthorized error: {}", authException.getMessage());
        response.setContentType("application/json");
        response.setStatus(401);
        response.getWriter().print("{ \"httpStatus\": \"UNAUTHORIZED\", \"message\": \"Authentication error\", \"errors\": [\"Token validation failed\"] }");
    }
    
}
