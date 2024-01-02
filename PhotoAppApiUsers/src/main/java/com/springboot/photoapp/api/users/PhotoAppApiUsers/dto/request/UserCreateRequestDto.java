package com.springboot.photoapp.api.users.PhotoAppApiUsers.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserCreateRequestDto {

    private String userId;
    @NotBlank(message = "firstName can not be blank.")
    private String firstName;
    @NotBlank(message = "lastName can not be blank.")
    private String lastName;
    @NotBlank(message = "email can not be blank.")
    @Email
    private String email;
    @NotBlank(message = "password can not be blank.")
    @Size(min = 8, max = 16, message = "password can not be less than 8 and more than 16 characters.")
    private String password;
}
