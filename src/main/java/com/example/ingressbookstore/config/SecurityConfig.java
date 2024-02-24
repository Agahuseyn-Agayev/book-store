package com.example.ingressbookstore.config;

import com.example.ingressbookstore.model.enums.UserType;
import com.example.ingressbookstore.service.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.example.ingressbookstore.model.enums.UserType.AUTHOR;
import static com.example.ingressbookstore.model.enums.UserType.STUDENT;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTConfig jwtConfig;

//
    @Override
    protected void configure(HttpSecurity http) throws Exception {

//        http.csrf().disable()
//
//                .authorizeRequests()
//                .antMatchers("v1/auth/**").permitAll()
//                .anyRequest()
//                .authenticated()
//                .and()
//                .addFilterBefore(new JwtFilter(jwtService),UsernamePasswordAuthenticationFilter.class);
//
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/v1/auth/**").permitAll()
                .and().httpBasic();
        http.apply(jwtConfig);

    }

}

