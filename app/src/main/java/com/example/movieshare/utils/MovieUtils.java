package com.example.movieshare.utils;

public class MovieUtils {
    public static void simulateSleeping() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
