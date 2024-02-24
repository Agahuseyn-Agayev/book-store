package com.example.ingressbookstore.model.request;

import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignInRequest {
  @Email( message = "Invalid Email")
  @Size(min = 10, message = "Invalid email: Must be of 10 characters")
  String email;

  @NotBlank(message = "Invalid password: Empty password")
  @NotNull(message = "Invalid password: Password is null")
  @Size(min = 5, message = "Invalid password: Must be of 5 characters")
  String password;
}
