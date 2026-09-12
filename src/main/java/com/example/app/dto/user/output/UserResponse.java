package com.example.app.dto.user.output;

import java.time.LocalDate;
import java.util.UUID;

public record UserResponse(
    UUID id,

    String name,
    
    String email,

    LocalDate emailVerificationTime,

    String password,

    String phoneNumber
) {}
