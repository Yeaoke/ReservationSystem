package com.example.app.dto.input;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;


public record CreateReservationDTO(
    
    @NotNull(message = "")
    Long room_id,
    
    @NotNull(message = "date can't be empty")
    @FutureOrPresent(message = "start_date can't use past days")
    LocalDate start_date,
    
    @NotNull(message = "date can't be empty")
    @Future(message = "end_date can only be in future")
    LocalDate end_date
    
) {}


