package com.example.ingressbookstore.controller;

import com.example.ingressbookstore.model.request.AuthPayloadDto;
import com.example.ingressbookstore.model.request.SignInRequest;
import com.example.ingressbookstore.model.request.SignUpRequest;
import com.example.ingressbookstore.model.response.TokenResponse;
import com.example.ingressbookstore.service.UserService;
import com.example.ingressbookstore.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final JwtService tokenService;

    @PostMapping("/signUp")
    @ResponseStatus(CREATED)
    public void signUp(@RequestBody @Valid SignUpRequest request) {
        userService.signUp(request);
    }

    @PostMapping("/signIn")
    public TokenResponse sigIn(@RequestBody @Valid SignInRequest request) {
        return userService.signIn(request);
    }

    @PostMapping("/refresh-token")
    public TokenResponse refreshToken(@RequestHeader String refreshToken) {
        return userService.refreshTokens(refreshToken);
    }

    @PostMapping("/validate-token")
    public AuthPayloadDto validateToken(@RequestHeader String accessToken) {
        return tokenService.validateToken(accessToken);
    }

}
