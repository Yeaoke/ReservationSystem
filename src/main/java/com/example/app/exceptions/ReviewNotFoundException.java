package com.example.app.exceptions;

public class ReviewNotFoundException extends Exception {
    public ReviewNotFoundException() {
        super("Review not found");
    }
}
