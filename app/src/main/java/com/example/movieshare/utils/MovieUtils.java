package com.example.movieshare.utils;

public class MovieUtils {
    private static final Integer NUM_OF_MILLIS_TO_WAIT = 2000;

    public static void simulateSleeping() {
        try {
            Thread.sleep(NUM_OF_MILLIS_TO_WAIT);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
