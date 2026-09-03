package com.example.app.exceptions;

public class RoomAlreadyReservedException extends RuntimeException { 
    public RoomAlreadyReservedException(String message) {
        super(message);
    }
}
