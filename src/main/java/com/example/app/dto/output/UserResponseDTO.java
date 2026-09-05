package com.example.app.dto.output;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponseDTO(
    UUID id,

    String name,
    
    String email,

    LocalDate emailVerificationTime,

    String password,

    String phoneNumber
) {}
