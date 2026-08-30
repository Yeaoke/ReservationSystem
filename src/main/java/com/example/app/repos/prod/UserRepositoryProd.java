package com.example.app.repos.prod;

import org.springframework.context.annotation.Profile;

import com.example.app.repos.UserRepository;


@Profile("prod")
public interface UserRepositoryProd extends UserRepository {}
