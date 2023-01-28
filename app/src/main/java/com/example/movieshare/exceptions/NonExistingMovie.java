package com.example.movieshare.exceptions;

public class NonExistingMovie extends Exception {
    public NonExistingMovie(String errorMessage, Throwable error) {
        super(errorMessage, error);
    }
}
