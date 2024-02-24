package com.example.ingressbookstore.service;

import com.example.ingressbookstore.entity.UserEntity;
import com.example.ingressbookstore.exception.AuthException;
import com.example.ingressbookstore.model.jwt.AccessTokenClaimsSet;
import com.example.ingressbookstore.model.jwt.RefreshTokenClaimsSet;
import com.example.ingressbookstore.model.request.AuthPayloadDto;
import com.example.ingressbookstore.model.response.TokenResponse;
import com.example.ingressbookstore.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.ingressbookstore.mapper.factory.TokenFactory.buildAccessTokenClaimsSet;
import static com.example.ingressbookstore.mapper.factory.TokenFactory.buildRefreshTokenClaimsSet;
import static com.example.ingressbookstore.model.constant.ExceptionConstants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class JwtService {
    private final JwtUtil jwtUtil;
    private final KeyPair publicPrivateKeys;

    @Value("${jwt.accessToken.expiration.time}")
    private Duration accessTokenExpirationTime;
    ;

    @Value("${jwt.refreshToken.expiration.time}")
    private Duration refreshTokenExpirationTime;

    public TokenResponse generateToken(UserEntity user, int refreshTokenExpirationCount) {
        refreshTokenExpirationCount = 50;

        var accessTokenClaimsSet = buildAccessTokenClaimsSet(
                user,
                jwtUtil.generateSessionExpirationTime(accessTokenExpirationTime)
        );

        var refreshTokenClaimsSet = buildRefreshTokenClaimsSet(
                user,
                refreshTokenExpirationCount,
                jwtUtil.generateSessionExpirationTime(refreshTokenExpirationTime)
        );

        var accessToken = jwtUtil.generateToken(accessTokenClaimsSet, publicPrivateKeys.getPrivate());
        var refreshToken = jwtUtil.generateToken(refreshTokenClaimsSet, publicPrivateKeys.getPrivate());

        return TokenResponse.of(accessToken, refreshToken);
    }


    public AuthPayloadDto validateToken(String accessToken) {
        try {
            AccessTokenClaimsSet claimsFromAccessToken = jwtUtil.getClaimsFromAccessToken(accessToken);
            var userId = claimsFromAccessToken.getUserId();
            var publicKey = publicPrivateKeys.getPublic();

            jwtUtil.verifyToken(accessToken, (RSAPublicKey) publicKey);

            if (jwtUtil.isTokenExpired(claimsFromAccessToken.getExpirationTime())) {
                throw new AuthException(TOKEN_EXPIRED_MESSAGE, TOKEN_EXPIRED_CODE, 406);
            }

            return AuthPayloadDto.of(userId);
        } catch (AuthException ex) {
            throw ex;
        } catch (Exception ex) {
            log.error(String.valueOf(ex));
            throw new AuthException(USER_UNAUTHORIZED_MESSAGE, USER_UNAUTHORIZED_CODE, 401);
        }
    }

    public Authentication buildAuthentication(String accessToken) {
        AccessTokenClaimsSet claimsSet = jwtUtil.getClaimsFromAccessToken(accessToken);

        String username = claimsSet.getEmail();

        List<String> roles = claimsSet.getRoles();

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        User principal = new User(username, "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public TokenResponse refreshToken(UserEntity userEntity, String refreshToken, RefreshTokenClaimsSet refreshTokenClaimsSet) {
        try {
            var publicKey = publicPrivateKeys.getPublic();

            jwtUtil.verifyToken(refreshToken, (RSAPublicKey) publicKey);

            if (jwtUtil.isRefreshTokenTimeExpired(refreshTokenClaimsSet)) {
                throw new AuthException(REFRESH_TOKEN_EXPIRED_MESSAGE, USER_UNAUTHORIZED_CODE, 401);
            }

            if (jwtUtil.isRefreshTokenCountExpired(refreshTokenClaimsSet)) {
                throw new AuthException(REFRESH_TOKEN_COUNT_EXPIRED_MESSAGE, USER_UNAUTHORIZED_CODE, 401);
            }

            return generateToken(userEntity, refreshTokenClaimsSet.getCount() - 1);
        } catch (AuthException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new AuthException(USER_UNAUTHORIZED_MESSAGE, USER_UNAUTHORIZED_CODE, 401);
        }
    }

    public boolean isTokenValid(String token) {
        try {
            // Parse and verify the token
            jwtUtil.verifyToken(token, (RSAPublicKey) publicPrivateKeys.getPublic());

            // Additional custom validation logic if needed
            AccessTokenClaimsSet claimsSet = jwtUtil.getClaimsFromAccessToken(token);
            // Add custom logic to validate claims (e.g., issuer, audience, etc.)

            // Check if the token is expired
            if (jwtUtil.isTokenExpired(claimsSet.getExpirationTime())) {
                return false;
            }

            // Token is considered valid
            return true;
        } catch (AuthException ex) {
            // Handle your custom exception for token verification failures
            throw ex;
        } catch (Exception ex) {
            // Log and handle other exceptions
            log.error("Error validating token", ex);
            return false;
        }
    }
}