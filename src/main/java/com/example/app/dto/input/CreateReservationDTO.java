package com.example.app.dto.input;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record CreateReservationDTO(

        @NotNull(message = "room_id can't be null")
        UUID roomId,

        @NotNull(message = "start_date can't be null")
        @FutureOrPresent(message = "start_date can't use past days")
        LocalDate start_date,

        @NotNull(message = "end_date can't be null")
        @Future(message = "end_date must be in the future")
        LocalDate end_date

) {
}
