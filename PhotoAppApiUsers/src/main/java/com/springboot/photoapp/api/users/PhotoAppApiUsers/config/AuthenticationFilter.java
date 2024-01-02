package com.springboot.photoapp.api.users.PhotoAppApiUsers.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.request.LoginRequestDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.service.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.ArrayList;

public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private UserService userService;
    private Environment environment;

    public AuthenticationFilter(AuthenticationManager authenticationManager,
                                UserService userService,
                                Environment environment) {
        super(authenticationManager);
        this.userService = userService;
        this.environment = environment;
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            LoginRequestDto credentials = new ObjectMapper()
                    .readValue(request.getInputStream(), LoginRequestDto.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getEmail(),
                            credentials.getPassword(),
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
//        super.successfulAuthentication(request, response, chain, authResult);

        String userName = ((User) authResult.getPrincipal()).getUsername();
    }
}
