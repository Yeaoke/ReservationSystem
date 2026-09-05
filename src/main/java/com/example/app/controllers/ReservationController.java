package com.example.app.controllers;

import com.example.app.dto.ReservationStatus;
import com.example.app.dto.input.CreateReservationDTO;
import com.example.app.dto.output.ReservationResponseDTO;
import com.example.app.models.Reservation;
import com.example.app.models.Review;
import com.example.app.services.ReservationService;
import com.example.app.services.ReviewService;

import jakarta.validation.Valid;
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
    public ResponseEntity<ReservationResponseDTO> getReservationById(
            @PathVariable UUID id
    ) {
        return reservationService.findReservation(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<ReservationResponseDTO> createReservation(
            @PathVariable UUID userId,
            @RequestBody @Valid CreateReservationDTO dto
    ) {
        Reservation reservation = reservationService.createReservation(dto, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(toDto(reservation));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ReservationResponseDTO> updateReservation(
            @PathVariable UUID id,
            @RequestBody @Valid CreateReservationDTO dto
    ) {
        Reservation reservation = reservationService.updateReservation(id, dto);
        return ResponseEntity.ok(toDto(reservation));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ReservationResponseDTO> updateReservationStatus(
            @PathVariable UUID id,
            @RequestParam ReservationStatus status
    ) {
        return ResponseEntity.ok(toDto(
                reservationService.updateReservationStatus(id, status)
        ));
    }

    @GetMapping("/{reservationId}/reviews/{reviewId}")
    public ResponseEntity<Review> getReviewOfReservation(
            @PathVariable UUID reservationId,
            @PathVariable UUID reviewId
    ) {
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
        reservationService.deleteReservation(id);
        return ResponseEntity.noContent().build();
    }

    private ReservationResponseDTO toDto(Reservation reservation) {
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
