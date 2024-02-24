package com.example.ingressbookstore.mapper;

import com.example.ingressbookstore.entity.UserEntity;
import com.example.ingressbookstore.model.request.SignUpRequest;
import com.example.ingressbookstore.model.response.UserResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserMapper {

    public static UserResponse builderUserResponse(UserEntity userEntity) {
        return UserResponse.builder()
                .email(userEntity.getEmail())
                .userType(userEntity.getUserType())
                .build();
    }

    public static UserEntity builderUserEntity(SignUpRequest request,BCryptPasswordEncoder bCryptPasswordEncoder) {
        return UserEntity.builder()
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .userType(request.getUserType())
                .build();
    }

}
