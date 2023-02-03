package com.example.movieshare.utils;

public class MovieUtils {
    public static void simulateSleeping() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
