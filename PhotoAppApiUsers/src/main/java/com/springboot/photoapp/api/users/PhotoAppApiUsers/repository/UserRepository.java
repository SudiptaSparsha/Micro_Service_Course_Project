package com.springboot.photoapp.api.users.PhotoAppApiUsers.repository;

import com.springboot.photoapp.api.users.PhotoAppApiUsers.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findByEmail(String email);
}
