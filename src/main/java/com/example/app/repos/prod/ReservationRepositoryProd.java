package com.example.app.repos.prod;

import org.springframework.context.annotation.Profile;

import com.example.app.repos.ReservationRepository;

@Profile("prod")
public interface ReservationRepositoryProd extends ReservationRepository {}
