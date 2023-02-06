package com.example.movieshare.repository.room.handlers;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.movieshare.listeners.ExecuteMovieItemListener;
import com.example.movieshare.listeners.GetMovieItemListListener;
import com.example.movieshare.listeners.GetMovieItemListener;
import com.example.movieshare.repository.room.localdb.AppLocalDB;
import com.example.movieshare.repository.room.localdb.AppLocalDbRepository;
import com.example.movieshare.repository.models.MovieComment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

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

    public void getAllMovieComments(GetMovieItemListListener<MovieComment> listener) {
        this.executor.execute(() -> {
            List<MovieComment> movieComments = localDB.movieCommentDao().getAllMovieComments();
            mainThreadHandler.post(() -> listener.onComplete(movieComments));
        });
    }

    public void getMovieCommentById(Integer id, GetMovieItemListener<MovieComment> listener) {
        this.executor.execute(() -> {
            MovieComment movieComment = localDB.movieCommentDao().getMovieCommentById(id);
            mainThreadHandler.post(() -> listener.onComplete(movieComment));
        });
    }

    public void getAllMovieCommentsByUserId(Integer userId,
                                            GetMovieItemListListener<MovieComment> listener){
        this.executor.execute(() -> {
            List<MovieComment> allMovieComments = localDB.movieCommentDao().getAllMovieComments();
            List<MovieComment> filteredMovieComments = allMovieComments
                            .stream()
                            .filter(movieComment -> movieComment.getUserId().equals(userId))
                            .collect(Collectors.toList());
            mainThreadHandler.post(() -> listener.onComplete(filteredMovieComments));
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

    public void addMovieComment(MovieComment movieComment, ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            localDB.movieCommentDao().insertAll(movieComment);
            mainThreadHandler.post(listener::onComplete);
        });
    }

    public void removeMovieComment(Integer index, ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            MovieComment deletedMovieComment =
                    localDB.movieCommentDao().getAllMovieComments().get(index);
            localDB.movieCommentDao().delete(deletedMovieComment);
            mainThreadHandler.post(listener::onComplete);
        });
    }

    public void updateMovieComment(Integer index, MovieComment movieComment,
                                   ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            MovieComment deletedMovieComment =
                    localDB.movieCommentDao().getAllMovieComments().get(index);
            localDB.movieCommentDao().delete(deletedMovieComment);
            localDB.movieCommentDao().insertAll(movieComment);
            mainThreadHandler.post(listener::onComplete);
        });
    }
}