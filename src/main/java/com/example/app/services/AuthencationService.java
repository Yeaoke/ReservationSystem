package com.example.app.services;

import org.springframework.stereotype.Service;

import com.example.app.repos.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class AuthencationService {
    
    private UserRepository userRepository;

    public String login() {
        String token = "";
        return token;
    }
    public void logout() {}
    public void refreshToken() {}
    public void getAccessToken() {}
    public void checkCredintails() {}
}
