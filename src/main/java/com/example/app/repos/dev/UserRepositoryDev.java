package com.example.app.repos.dev;

import org.springframework.context.annotation.Profile;

import com.example.app.repos.UserRepository;


@Profile("dev")
public interface UserRepositoryDev extends UserRepository {}
