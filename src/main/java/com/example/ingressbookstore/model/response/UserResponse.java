package com.example.ingressbookstore.model.response;

import com.example.ingressbookstore.model.enums.UserType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String email;
    String password;
    UserType userType;
}
