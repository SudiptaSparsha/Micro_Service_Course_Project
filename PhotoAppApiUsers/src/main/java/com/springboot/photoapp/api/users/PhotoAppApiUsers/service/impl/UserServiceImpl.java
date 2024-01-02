package com.springboot.photoapp.api.users.PhotoAppApiUsers.service.impl;

import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.request.UserCreateRequestDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.response.UserCreateResponseDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.entity.User;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.repository.UserRepository;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    UserRepository userRepository;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserCreateResponseDto createUser(UserCreateRequestDto createUserRequestDto) {
        createUserRequestDto.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        User user = modelMapper.map(createUserRequestDto, User.class);
        user.setPassword(bCryptPasswordEncoder.encode(createUserRequestDto.getPassword()));
        userRepository.save(user);

        return modelMapper.map(user, UserCreateResponseDto.class);
    }
}
