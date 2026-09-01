package com.example.app.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.models.Room;

public interface RoomRepository extends JpaRepository<Room, UUID> {}
