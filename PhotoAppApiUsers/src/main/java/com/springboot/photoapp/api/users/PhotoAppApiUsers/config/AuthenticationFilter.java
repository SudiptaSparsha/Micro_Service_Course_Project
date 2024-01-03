package com.springboot.photoapp.api.users.PhotoAppApiUsers.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.request.LoginRequestDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.response.UserResponseDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.service.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;

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
        UserResponseDto userResponseDto = userService.findByUserName(userName);

        String tokenSecret = environment.getProperty("token.secret");
        byte[] secretKeyBytes = Base64.getEncoder().encode(tokenSecret.getBytes());
        SecretKey secretKey = new SecretKeySpec(secretKeyBytes, SignatureAlgorithm.HS512.getJcaName());

        String token = Jwts.builder()
                .setSubject(userResponseDto.getUserId())
                .setExpiration(Date.from(Instant.now().plusMillis(
                        Long.parseLong(Objects.requireNonNull(environment.getProperty("token.expiration-time"))))))
                .setIssuedAt(Date.from(Instant.now()))
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();

        response.addHeader("token", token);
        response.addHeader("userId", userResponseDto.getUserId());
    }
}
