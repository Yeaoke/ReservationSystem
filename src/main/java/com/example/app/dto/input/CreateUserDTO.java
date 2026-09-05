package com.example.app.dto.input;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record CreateUserDTO(

    @NotNull(message = "Name can't be empty")
    @Size(min = 2, max = 100)
    String name,

    @Email(message = "input correct email")
    @NotNull
    String email,

    @Pattern(
        regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$",
        message = "Пароль должен содержать: минимум 8 символов, цифру, заглавную и строчную букву, спецсимвол (@#$%^&+=!)"
    )
    @NotBlank
    @Size(min = 8)
    String password,

    @Pattern(
        regexp = "^\\+[1-9]\\d{1,14}$",
        message = "Number must be +*"
    )
    @NotNull
    String phoneNumber
) {}
