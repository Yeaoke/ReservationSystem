package com.example.app.repos.prod;

import java.util.UUID;

import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.models.Room;


@Profile("prod")
public interface RoomRepositoryProd extends JpaRepository<Room, UUID> {}
