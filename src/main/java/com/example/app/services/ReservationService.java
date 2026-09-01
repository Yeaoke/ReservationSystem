package com.example.app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.example.app.dto.ReservationStatus;
import com.example.app.dto.input.CreateReservationDTO;
import com.example.app.exceptions.RoomNotFoundException;
import com.example.app.exceptions.UserNotFoundException;
import com.example.app.models.Reservation;
import com.example.app.models.Room;
import com.example.app.models.User;
import com.example.app.repos.ReservationRepository;
import com.example.app.repos.ReviewRepository;
import com.example.app.repos.RoomRepository;
import com.example.app.repos.UserRepository;

import lombok.extern.log4j.Log4j2;
import tools.jackson.databind.ObjectMapper;

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

    public void createReservation(
        CreateReservationDTO dto,
        UUID userId
    ) {

        ReentrantLock lock = new ReentrantLock();

        lock.lock();
        try {
            Reservation reservation = new Reservation();

            User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

            Room room = roomRepository.findById(dto.roomId())
                .orElseThrow(RoomNotFoundException::new);

            reservation.setId(UUID.randomUUID());
            reservation.setUser(user);
            reservation.setRoom(room);
            reservation.setStartDate(dto.start_date());
            reservation.setEndDate(dto.end_date());
            reservation.setStatus(ReservationStatus.PENDING);
            log.info("Reservation was created");

            reservationRepository.save(reservation);
        } catch (UserNotFoundException | RoomNotFoundException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public Optional<Reservation> getReservationById(UUID id) {
        return reservationRepository.findById(id);
    }

    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    public Reservation createReservation(Reservation reservation) {
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
    /*
    public List<Reservation> getReservationsByDate(LocalDate date) {


        return reservationRepository.findAll(findByDate, sort);
    }
    */
}