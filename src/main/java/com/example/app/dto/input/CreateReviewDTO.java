package com.example.app.dto.input;

import java.util.UUID;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record CreateReviewDTO(

    @NotNull(message = "reservation id can't be empty")
    UUID reservationId,

    @NotNull()
    @Min(value = 1, message = "minimum for rating is 1")
    @Max(value = 10, message = "maximum for rating is 10")
    Integer rating
) {}