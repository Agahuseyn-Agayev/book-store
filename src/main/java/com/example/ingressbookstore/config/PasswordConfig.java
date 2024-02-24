package com.example.ingressbookstore.config;

import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

@Configuration
public class PasswordConfig {
//    @Bean
//    public JwtFilter jwtAuthenticationFilter() {
//        return new JwtFilter();
//    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    public static PublicKey publicKey(Path path)
            throws Exception {
        byte[] keyBytes = Files.readAllBytes(path);
        X509EncodedKeySpec spec =
                new X509EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePublic(spec);
    }

    public static PrivateKey privateKey(Path path)
            throws Exception {
        byte[] keyBytes = Files.readAllBytes(path);
        PKCS8EncodedKeySpec spec =
                new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory kf = KeyFactory.getInstance("RSA");
        return kf.generatePrivate(spec);
    }

    @SneakyThrows
    @Bean
    public KeyPair publicPrivateKeys() {
        PrivateKey privateKey = privateKey(Path.of(ClassLoader.getSystemResource("my_private.cert").toURI()));
        PublicKey publicKey = publicKey(Path.of(ClassLoader.getSystemResource("my_public.cert").toURI()));
        return new KeyPair(publicKey, privateKey);
    }
}
