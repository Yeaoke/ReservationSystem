package com.example.app.repos.dev;

import org.springframework.context.annotation.Profile;

import com.example.app.repos.RoomRepository;


@Profile("dev")
public interface RoomRepositoryDev extends RoomRepository {}
