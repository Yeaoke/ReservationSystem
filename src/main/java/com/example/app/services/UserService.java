package com.example.app.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dto.input.CreateUserDTO;
import com.example.app.models.User;
import com.example.app.repos.UserRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;

    public User createUser(
        CreateUserDTO dto
    ) {
        User user = new User();

        user.setId(UUID.randomUUID());
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setEmailVerificationTime(LocalDate.now());
        user.setPassword(dto.password());
        user.setPhoneNumber(dto.phoneNumber());

        return userRepository.save(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsersWhoMadeReview() {
        // норм логика
        return userRepository.findAll();
    }
}
