package com.springboot.photoapp.api.users.PhotoAppApiUsers.service;

import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.request.UserCreateRequestDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.response.UserCreateResponseDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    public UserCreateResponseDto createUser(UserCreateRequestDto createUserRequestDto);
}
