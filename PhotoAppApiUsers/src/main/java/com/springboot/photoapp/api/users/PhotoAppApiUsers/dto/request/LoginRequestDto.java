package com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginRequestDto {

    private String email;
    private String password;

}
