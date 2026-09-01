package com.example.app.exceptions;

public class ReservationNotFoundException extends Exception {
    public ReservationNotFoundException() {
        super("Reservation not found");
    }
}
