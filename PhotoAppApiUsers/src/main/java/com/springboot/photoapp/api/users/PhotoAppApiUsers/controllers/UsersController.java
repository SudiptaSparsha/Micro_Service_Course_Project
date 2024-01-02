package com.springboot.photoapp.api.users.PhotoAppApiUsers.controllers;

import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.request.UserCreateRequestDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.response.UserResponseDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private Environment environment;

    private UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/status/check")
    public String status(){
        return "Users Controller Working on port number : " + environment.getProperty("local.server.port");
    }

    @PostMapping
    public ResponseEntity<UserResponseDto> createUser(@Valid @RequestBody UserCreateRequestDto createUserRequestDto) {
        UserResponseDto responseDto = userService.createUser(createUserRequestDto);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }
}
