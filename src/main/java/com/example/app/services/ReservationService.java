package com.example.app.services;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.example.app.models.Reservation;
import com.example.app.repos.ReservationRepository;
import com.example.app.repos.ReviewRepository;
import com.example.app.repos.RoomRepository;
import com.example.app.repos.UserRepository;
import com.example.app.repos.dev.ReservationRepositoryDev;
import com.example.app.repos.dev.ReviewRepositoryDev;
import com.example.app.repos.dev.RoomRepositoryDev;
import com.example.app.repos.dev.UserRepositoryDev;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RoomRepository roomRepository;

    public Optional<Reservation> getReservationById(UUID id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation() {
        Reservation reservation = new Reservation();

        //UUID user_id = authicationService.getCurr

        //reservation.setId();

        return reservationRepository.save(reservation);
    }

    public Optional<Reservation> findReservation(UUID id) {
        return reservationRepository.findById(id);
    }

    public void deleteReservation(UUID id) {
        reservationRepository.deleteById(id);
    }

    public Reservation updateReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }

    @Profile("dev")
    public List<Reservation> findReservationsByDate() {
        return reservationRepository.findAll(Sort.by(Sort.Direction.ASC,"start_date", "status"));
    }
}