package com.springboot.photoapp.api.users.PhotoAppApiUsers.repository;

import com.springboot.photoapp.api.users.PhotoAppApiUsers.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
