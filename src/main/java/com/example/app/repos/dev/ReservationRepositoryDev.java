package com.example.app.repos.dev;

import org.springframework.context.annotation.Profile;

import com.example.app.repos.ReservationRepository;

@Profile("dev")
public interface ReservationRepositoryDev extends ReservationRepository {}
