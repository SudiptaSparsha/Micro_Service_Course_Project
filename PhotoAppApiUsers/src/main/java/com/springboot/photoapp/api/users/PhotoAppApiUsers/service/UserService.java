package com.springboot.photoapp.api.users.PhotoAppApiUsers.service;

import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.request.UserCreateRequestDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.response.UserCreateResponseDto;

public interface UserService {
    public UserCreateResponseDto createUser(UserCreateRequestDto createUserRequestDto);
}
