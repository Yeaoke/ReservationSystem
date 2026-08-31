package com.example.app.dto.input;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;


public record CreateReservationDTO(
    
    @NotEmpty(message = "room_id shouldn't be empty")
    UUID roomId,
    
    @NotNull(message = "date can't be empty")
    @FutureOrPresent(message = "start_date can't use past days")
    LocalDate start_date,
    
    @NotNull(message = "date can't be empty")
    @Future(message = "end_date can only be in future")
    LocalDate end_date
    
) {}


