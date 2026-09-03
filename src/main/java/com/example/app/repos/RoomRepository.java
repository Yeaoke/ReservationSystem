package com.example.app.repos;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.app.models.Room;

import jakarta.persistence.LockModeType;

public interface RoomRepository extends JpaRepository<Room, UUID> {

    @Query("SELECT r FROM Room r WHERE r.id = :id")
    @Lock(LockModeType.PESSIMISTIC_WRITE) 
    Optional<Room> findByIdForUpdate(@Param("id") UUID id);
}
