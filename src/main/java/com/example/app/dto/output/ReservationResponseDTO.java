package com.example.app.dto.output;

import java.time.LocalDate;
import java.util.UUID;

import com.example.app.dto.ReservationStatus;

public record ReservationResponseDTO(
    UUID userId,

    UUID roomId,

    LocalDate startDate,

    LocalDate endDate,

    Long price,

    ReservationStatus status
) {}
