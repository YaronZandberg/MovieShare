package com.example.movieshare.repository.room.handlers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.example.movieshare.listeners.movies.*;
import com.example.movieshare.repository.room.localdb.AppLocalDB;
import com.example.movieshare.repository.room.localdb.AppLocalDbRepository;
import com.example.movieshare.repository.models.MovieComment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MovieCommentHandler {
    private static final MovieCommentHandler movieCommentHandlerInstance = new MovieCommentHandler();
    private final Executor executor;
    private final Handler mainThreadHandler;
    private final AppLocalDbRepository localDB;

    private MovieCommentHandler() {
        this.executor = Executors.newSingleThreadExecutor();
        this.mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        this.localDB = AppLocalDB.getAppDB();
    }

    public static MovieCommentHandler instance() {
        return movieCommentHandlerInstance;
    }

    public LiveData<List<MovieComment>> getAllMovieComments() {
        return this.localDB.movieCommentDao().getAllMovieComments();
    }

    public void getAllMovieCommentsByUserId(String userId,
                                            GetMovieItemListListener<MovieComment> listener){
        this.executor.execute(() -> {
            List<MovieComment> movieComments =
                    localDB.movieCommentDao().getAllMovieCommentsByUserId(userId);
            mainThreadHandler.post(() -> listener.onComplete(movieComments));
        });
    }

    public void getAllMovieCommentsByMovieId(String movieId,
                                             GetMovieItemListListener<MovieComment> listener){
        this.executor.execute(() -> {
            List<MovieComment> movieComments =
                    localDB.movieCommentDao().getAllMovieCommentsByMovieId(movieId);
            mainThreadHandler.post(() -> listener.onComplete(movieComments));
        });
    }

    public void addMovieComment(MovieComment movieComment) {
        try {
            List<MovieComment> currentMovieComments = this.localDB.movieCommentDao().getAllMovieCommentsCurrent();
            if(currentMovieComments.stream().anyMatch(movieComment1 -> movieComment1.getMovieCommentId().equals(movieComment.getMovieCommentId()))) {
                this.localDB.movieCommentDao().updateAll(movieComment);
            } else {
                this.localDB.movieCommentDao().insertAll(movieComment);
            }
        } catch (Exception e) {
            Log.d("TAG", e.getMessage());
        }
    }
}
