package com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
}
