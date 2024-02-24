package com.example.ingressbookstore.mapper.factory;


import com.example.ingressbookstore.entity.UserEntity;
import com.example.ingressbookstore.model.jwt.AccessTokenClaimsSet;
import com.example.ingressbookstore.model.jwt.RefreshTokenClaimsSet;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

import static com.example.ingressbookstore.model.constant.AuthConstants.ISSUER;
import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class TokenFactory {

    public static AccessTokenClaimsSet buildAccessTokenClaimsSet(UserEntity user, Date expirationTime) {

        return AccessTokenClaimsSet.builder()
                .iss(ISSUER)
                .userId(user.getId())
                .email(user.getEmail())
                .roles(List.of(user.getUserType().toString()))
                .createdTime(new Date())
                .expirationTime(expirationTime)
                .build();
    }

    public static RefreshTokenClaimsSet buildRefreshTokenClaimsSet(UserEntity user, int refreshTokenExpirationCount, Date expirationTime) {

        return RefreshTokenClaimsSet.builder()
                .iss(ISSUER)
                .userId(user.getId())
                .email(user.getEmail())
                .roles(List.of(user.getUserType().toString()))
                .expirationTime(expirationTime)
                .count(refreshTokenExpirationCount)
                .build();
    }
}
