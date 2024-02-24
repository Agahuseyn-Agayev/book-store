package com.example.ingressbookstore.service;

import com.example.ingressbookstore.entity.UserEntity;
import com.example.ingressbookstore.exception.AuthException;
import com.example.ingressbookstore.exception.EmailExitsException;
import com.example.ingressbookstore.exception.NotFoundException;
import com.example.ingressbookstore.exception.PasswordMismatchException;
import com.example.ingressbookstore.model.request.SignInRequest;
import com.example.ingressbookstore.model.request.SignUpRequest;
import com.example.ingressbookstore.model.response.TokenResponse;
import com.example.ingressbookstore.repository.UserRepository;
import com.example.ingressbookstore.util.JwtUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.interfaces.RSAPublicKey;

import static com.example.ingressbookstore.mapper.UserMapper.builderUserEntity;
import static com.example.ingressbookstore.model.constant.ExceptionConstants.*;
import static com.example.ingressbookstore.model.constant.ExceptionConstants.USER_UNAUTHORIZED_CODE;
import static com.example.ingressbookstore.model.enums.ExceptionMessage.*;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserService {
    final UserRepository userRepository;
    final JwtService jwtService;

    private final JwtUtil jwtUtil;

    final BCryptPasswordEncoder bCryptPasswordEncoder;

    public void signUp(SignUpRequest signUpRequest) {
        if (!verifyEmail(signUpRequest.getEmail())) {

            userRepository.save(builderUserEntity(signUpRequest, bCryptPasswordEncoder));
        } else {
            throw new EmailExitsException(EMAIL_EXITS.getMessage());
        }
    }

    public TokenResponse signIn(SignInRequest signInRequest) {
        var user = fetchUserEmailIfExits(signInRequest.getEmail());
        verifyPassword(signInRequest.getPassword(), user.getPassword());
        return jwtService.generateToken(user, 50);
    }

    public UserEntity fetchUserEmailIfExits(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(EMAIL_NOT_FOUND.getMessage())
        );

    }

    private void verifyPassword(String rawPassword, String hashedPassword) {
        if (!bCryptPasswordEncoder.matches(rawPassword, hashedPassword)) {
            throw new PasswordMismatchException(PASSWORD_DOES_NOT_MATCH.getMessage());
        }
    }

    private Boolean verifyEmail(String email) {
        return userRepository.existsByEmail(email);

    }


    public TokenResponse refreshTokens(String refreshToken) {
        var refreshTokenClaimsSet = jwtUtil.getClaimsFromRefreshToken(refreshToken);
        var userEntity =fetchUserEmailIfExits(refreshTokenClaimsSet.getEmail());
        return jwtService.refreshToken(userEntity,refreshToken,refreshTokenClaimsSet);
    }


}
