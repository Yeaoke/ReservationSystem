package com.example.app.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.models.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {}
