package com.example.app.repos;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.app.models.Review;

public interface ReviewRepository extends JpaRepository<Review, UUID> {}
