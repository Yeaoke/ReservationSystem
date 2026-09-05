package com.example.app.controllers;

import com.example.app.dto.input.CreateReservationDTO;
import com.example.app.dto.output.ReservationResponseDTO;
import com.example.app.models.Reservation;
import com.example.app.models.Review;
import com.example.app.services.ReservationService;
import com.example.app.services.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;
    private final ReviewService reviewService;

    @GetMapping("/{id}")
    public ResponseEntity<ReservationResponseDTO> getReservationById(@PathVariable UUID id) {
        Optional<Reservation> reservation = reservationService.findReservation(id);

        if (reservation.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(convertToReservationDTO(reservation.get()));
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Reservation> createReservation(
            @PathVariable UUID userId,
            @RequestBody CreateReservationDTO dto) {
        
        Reservation reservation = reservationService.createReservation(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(reservation);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Reservation> updateReservation(
            @PathVariable UUID id,
            @RequestBody CreateReservationDTO dto) {

        try {
            Reservation updatedReservation = reservationService.updateReservation(id, dto);
            return ResponseEntity.ok(updatedReservation);
        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{reservationId}/reviews/{reviewId}")
    public ResponseEntity<Review> getReviewOfReservation(
            @PathVariable UUID reservationId,
            @PathVariable UUID reviewId) {
        
        Optional<Review> review = reviewService.getReviewById(reviewId);

        if (review.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        if (!review.get().getReservation().getId().equals(reservationId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        return ResponseEntity.ok(review.get());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReservation(@PathVariable UUID id) {
        Optional<Reservation> reservationToDelete = reservationService.findReservation(id);

        if (reservationToDelete.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    private ReservationResponseDTO convertToReservationDTO(Reservation reservation) {
        return new ReservationResponseDTO(
                reservation.getUserId(),
                reservation.getRoomId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getPrice(),
                reservation.getStatus()
        );
    }
}