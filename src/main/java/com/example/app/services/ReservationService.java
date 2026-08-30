package com.example.app.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.example.app.dto.input.CreateReservationDTO;
import com.example.app.models.Reservation;
import com.example.app.reservationRepository.ReservationRepository;

@Service
public class ReservationService {

    private ReservationRepository reservationRepository;

    public Optional<Reservation> getReservationById(UUID id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    //public Reservation createReservation(CreateReservationDTO reservation) {

    //}
}