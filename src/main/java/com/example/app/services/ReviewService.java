package com.example.app.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.app.dto.input.CreateReviewDTO;
import com.example.app.exceptions.ReservationNotFoundException;
import com.example.app.models.Reservation;
import com.example.app.models.Review;
import com.example.app.repos.ReservationRepository;
import com.example.app.repos.ReviewRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ReviewService {
    
    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    public Review createReview(
        CreateReviewDTO dto,
        UUID reservationId
    )  {
        Review review = new Review();

        Reservation reservation = reservationRepository.findById(reservationId)
            .orElseThrow(() -> new ReservationNotFoundException(
                "Reservation not found with id" + dto.reservationId()
            ));

        review.setId(UUID.randomUUID());
        review.setReservation(reservation);
        review.setRating(dto.rating());
        
        return review;
    }

}
