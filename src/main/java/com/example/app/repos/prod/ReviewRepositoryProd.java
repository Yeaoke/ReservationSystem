package com.example.app.repos.prod;

import org.springframework.context.annotation.Profile;

import com.example.app.repos.ReviewRepository;

@Profile("prod")
public interface ReviewRepositoryProd extends ReviewRepository {}
