package com.example.movieshare.notifications;

import static com.example.movieshare.enums.LoadingState.NOT_LOADING;

import androidx.lifecycle.MutableLiveData;

import com.example.movieshare.enums.LoadingState;

public class NotificationManager {
    private static final NotificationManager notificationManagerInstance = new NotificationManager();
    private final MutableLiveData<LoadingState> eventMovieCategoryListLoadingState;
    private final MutableLiveData<LoadingState> eventMovieListLoadingState;
    private final MutableLiveData<LoadingState> eventMovieCommentListLoadingState;
    private final MutableLiveData<LoadingState> eventUserCommentListLoadingState;

    private NotificationManager() {
        this.eventMovieCategoryListLoadingState = new MutableLiveData<>(NOT_LOADING);
        this.eventMovieListLoadingState = new MutableLiveData<>(NOT_LOADING);
        this.eventMovieCommentListLoadingState = new MutableLiveData<>(NOT_LOADING);
        this.eventUserCommentListLoadingState = new MutableLiveData<>(NOT_LOADING);
    }

    public static NotificationManager instance() {
        return notificationManagerInstance;
    }

    public MutableLiveData<LoadingState> getEventMovieCategoryListLoadingState() {
        return this.eventMovieCategoryListLoadingState;
    }

    public MutableLiveData<LoadingState> getEventMovieListLoadingState() {
        return this.eventMovieListLoadingState;
    }

    public MutableLiveData<LoadingState> getEventMovieCommentListLoadingState() {
        return this.eventMovieCommentListLoadingState;
    }

    public MutableLiveData<LoadingState> getEventUserCommentListLoadingState() {
        return this.eventUserCommentListLoadingState;
    }
}
