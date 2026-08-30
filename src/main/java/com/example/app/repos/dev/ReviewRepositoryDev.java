package com.example.app.repos.dev;

import org.springframework.context.annotation.Profile;

import com.example.app.repos.ReviewRepository;

@Profile("dev")
public interface ReviewRepositoryDev extends ReviewRepository {}
