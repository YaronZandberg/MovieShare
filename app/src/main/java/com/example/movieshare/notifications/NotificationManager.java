package com.example.movieshare.notifications;

import static com.example.movieshare.enums.LoadingState.NOT_LOADING;

import androidx.lifecycle.MutableLiveData;

import com.example.movieshare.enums.LoadingState;

public class NotificationManager {
    private static final NotificationManager notificationManagerInstance = new NotificationManager();
    private final MutableLiveData<LoadingState> eventMovieCategoryListLoadingState;

    private NotificationManager() {
        this.eventMovieCategoryListLoadingState = new MutableLiveData<>(NOT_LOADING);
    }

    public static NotificationManager instance() {
        return notificationManagerInstance;
    }

    public MutableLiveData<LoadingState> getEventMovieCategoryListLoadingState() {
        return this.eventMovieCategoryListLoadingState;
    }
}
