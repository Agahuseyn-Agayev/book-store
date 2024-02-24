package com.example.ingressbookstore.service;

import com.example.ingressbookstore.model.enums.ExceptionMessage;
import com.example.ingressbookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       var user= userRepository.findByEmail(username)
               .orElseThrow(()-> new UsernameNotFoundException(ExceptionMessage.EMAIL_NOT_FOUND.getMessage()));

        List<GrantedAuthority> roles = List.of((GrantedAuthority) () -> user.getUserType().toString());
        return new User(user.getEmail(),user.getPassword(), roles);
    }
}
