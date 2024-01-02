package com.springboot.photoapp.api.users.PhotoAppApiUsers.service.impl;

import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.request.UserCreateRequestDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.response.UserResponseDto;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.entity.UserEntity;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.repository.UserRepository;
import com.springboot.photoapp.api.users.PhotoAppApiUsers.service.UserService;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public UserResponseDto createUser(UserCreateRequestDto createUserRequestDto) {
        createUserRequestDto.setUserId(UUID.randomUUID().toString());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        UserEntity userEntity = modelMapper.map(createUserRequestDto, UserEntity.class);
        userEntity.setPassword(bCryptPasswordEncoder.encode(createUserRequestDto.getPassword()));
        userRepository.save(userEntity);

        return modelMapper.map(userEntity, UserResponseDto.class);
    }

    @Override
    public UserResponseDto findByUserName(String email) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(userRepository.findByEmail(email), UserResponseDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);
        if (userEntity == null) throw new UsernameNotFoundException(username);
        return new User(userEntity.getEmail(), userEntity.getPassword(),
                true, true, true, true,
                new ArrayList<>());
    }
}
