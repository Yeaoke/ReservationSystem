package com.example.app.services;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.stereotype.Service;

import com.example.app.dto.ReservationStatus;
import com.example.app.dto.input.CreateReservationDTO;
import com.example.app.exceptions.DaysAmountException;
import com.example.app.exceptions.ReservationNotFoundException;
import com.example.app.exceptions.RoomAlreadyReservedException;
import com.example.app.exceptions.RoomNotFoundException;
import com.example.app.exceptions.UserNotFoundException;
import com.example.app.models.Reservation;
import com.example.app.models.Room;
import com.example.app.models.User;
import com.example.app.repos.ReservationRepository;
import com.example.app.repos.RoomRepository;
import com.example.app.repos.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
@RequiredArgsConstructor
public class ReservationService {

    private final Map<UUID, ReentrantLock> roomLocks = new ConcurrentHashMap<>();

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;
    private final RoomRepository roomRepository;

    @Transactional
    public Reservation createReservation(
            CreateReservationDTO dto,
            UUID userId
    ) {

        log.info("Creating reservation: userId={}, roomId={}, thread={}", userId, dto.roomId(), Thread.currentThread().getName());

        validateDates(dto);

        UUID roomId = dto.roomId();

        ReentrantLock roomLock = roomLocks.computeIfAbsent(roomId, id -> new ReentrantLock());

        roomLock.lock();

        try {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new UserNotFoundException(
                                    "User not found with id: " + userId
                            )
                    );

            Room room = roomRepository.findById(roomId)
                    .orElseThrow(() -> new RoomNotFoundException(
                                    "Room not found with id: " + roomId
                            )
                    );

            boolean available = reservationRepository.isRoomAvailableForPeriod(
                            room,
                            dto.start_date(),
                            dto.end_date()
                    );

            if (!available) {
                throw new RoomAlreadyReservedException(
                        "Room already reserved for this period"
                );
            }

            Reservation reservation = new Reservation();

            reservation.setId(UUID.randomUUID());
            reservation.setUser(user);
            reservation.setRoom(room);
            reservation.setStartDate(dto.start_date());
            reservation.setEndDate(dto.end_date());
            reservation.setStatus(ReservationStatus.APPROVED);

            Reservation savedReservation = reservationRepository.save(reservation);

            log.info("Reservation successfully created: reservationId={}, roomId={}, userId={}", savedReservation.getId(), roomId, userId);

            return savedReservation;

        } finally {

            roomLock.unlock();
            roomLocks.remove(roomId, roomLock);
        }
    }

    @Transactional
    public Reservation updateReservationStatus(
            UUID id,
            ReservationStatus status
    ) {

        log.info("Updating reservation status: id={}, status={}", id, status);

        Reservation reservation = reservationRepository.findById(id)
                        .orElseThrow(() -> new ReservationNotFoundException(
                                        "Reservation not found with id: " + id
                                )
                        );

        reservation.setStatus(status);

        return reservationRepository.save(reservation);
    }

    @Transactional
    public Optional<Reservation> getReservationById(UUID id) {
        return reservationRepository.findById(id);
    }

    @Transactional
    public List<Reservation> getAllReservations() {
        log.info("Getting all reservations");
        return reservationRepository.findAll();
    }

    @Transactional
    public Optional<Reservation> findReservation(UUID id) {
        return reservationRepository.findById(id);
    }

    @Transactional
    public void deleteReservation(UUID id) {

        Reservation reservation = reservationRepository.findById(id)
                        .orElseThrow(() -> new ReservationNotFoundException(
                                        "Reservation not found with id: " + id
                                )
                        );

        UUID roomId = reservation.getRoom().getId();

        ReentrantLock roomLock = roomLocks.computeIfAbsent(roomId, key -> new ReentrantLock());

        roomLock.lock();
        try {
            reservationRepository.delete(reservation);

            log.info("Reservation deleted: {}", id);

        } finally {

            roomLock.unlock();
            roomLocks.remove(roomId, roomLock);
        }
    }

    @Transactional
    public Reservation updateReservation(
            UUID reservationId,
            CreateReservationDTO dto
    ) {

        log.info("Updating reservation: reservationId={}, roomId={}", reservationId, dto.roomId());

        validateDates(dto);

        Reservation reservation = reservationRepository.findById(reservationId)
                        .orElseThrow(() -> new ReservationNotFoundException(
                                        "Reservation not found with id: " + reservationId
                                )
                        );

        UUID oldRoomId = reservation.getRoom().getId();
        UUID newRoomId = dto.roomId();

        if (oldRoomId.equals(newRoomId)) {

            ReentrantLock roomLock = roomLocks.computeIfAbsent(oldRoomId, id -> new ReentrantLock());

            roomLock.lock();

            try {
                boolean available = reservationRepository
                                .isRoomAvailableForPeriodWithReservation(
                                        reservationId,
                                        reservation.getRoom(),
                                        dto.start_date(),
                                        dto.end_date()
                                );

                if (!available) {
                    throw new RoomAlreadyReservedException(
                            "Room already reserved for this period"
                    );
                }

                reservation.setStartDate(dto.start_date());
                reservation.setEndDate(dto.end_date());

                return reservationRepository.save(reservation);

            } finally {
                roomLock.unlock();
                roomLocks.remove(oldRoomId, roomLock);
            }
        }

        UUID firstRoomId;
        UUID secondRoomId;

        if (oldRoomId.compareTo(newRoomId) < 0) {
            firstRoomId = oldRoomId;
            secondRoomId = newRoomId;
        } else {
            firstRoomId = newRoomId;
            secondRoomId = oldRoomId;
        }

        ReentrantLock firstLock = roomLocks.computeIfAbsent(firstRoomId, id -> new ReentrantLock());

        ReentrantLock secondLock = roomLocks.computeIfAbsent(secondRoomId, id -> new ReentrantLock());
        firstLock.lock();
        try {
            secondLock.lock();
            try {
                Room newRoom = roomRepository.findById(newRoomId)
                                .orElseThrow(() -> new RoomNotFoundException(
                                                "Room not found with id: " + newRoomId
                                        )
                                );

                boolean available = reservationRepository
                                .isRoomAvailableForPeriodWithReservation(
                                        reservationId,
                                        newRoom,
                                        dto.start_date(),
                                        dto.end_date()
                                );
                if (!available) {
                    throw new RoomAlreadyReservedException(
                            "Room already reserved for this period"
                    );
                }

                reservation.setRoom(newRoom);
                reservation.setStartDate(dto.start_date());
                reservation.setEndDate(dto.end_date());

                Reservation saved = reservationRepository.save(reservation);

                log.info("Reservation updated successfully: {}", reservationId);
                return saved;

            } finally {
                secondLock.unlock();
            }

        } finally {
            firstLock.unlock();

            roomLocks.remove(firstLock, firstLock);
            roomLocks.remove(secondRoomId, secondLock);
        }
    }

    private void validateDates(CreateReservationDTO dto) {

        if (dto.start_date() == null || dto.end_date() == null) {
            throw new DaysAmountException(
                    "Start date and end date can't be null"
            );
        }

        long daysBetween = ChronoUnit.DAYS.between(dto.start_date(), dto.end_date());

        if (!dto.start_date().isBefore(dto.end_date())) {
            throw new DaysAmountException(
                    "Start date must be before end date"
            );
        }

        if (daysBetween < 1) {
            throw new DaysAmountException(
                    "Reservation must be at least 1 day"
            );
        }

        if (daysBetween > 30) {
            throw new DaysAmountException(
                    "Reservation cannot be longer than 30 days"
            );
        }
    }
}