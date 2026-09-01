package com.example.app.exceptions;

public class RoomNotFoundException extends Exception {
    public RoomNotFoundException() {
        super("Room not found");
    }
}
