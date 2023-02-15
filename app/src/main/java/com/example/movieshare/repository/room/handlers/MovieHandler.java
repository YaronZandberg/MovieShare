package com.example.movieshare.repository.room.handlers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.example.movieshare.listeners.movies.*;
import com.example.movieshare.repository.room.localdb.AppLocalDB;
import com.example.movieshare.repository.room.localdb.AppLocalDbRepository;
import com.example.movieshare.repository.models.Movie;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MovieHandler {
    private static final MovieHandler movieHandlerInstance = new MovieHandler();
    private final Executor executor;
    private final Handler mainThreadHandler;
    private final AppLocalDbRepository localDB;

    private MovieHandler() {
        this.executor = Executors.newSingleThreadExecutor();
        this.mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        this.localDB = AppLocalDB.getAppDB();
    }

    public static MovieHandler instance() {
        return movieHandlerInstance;
    }

    public LiveData<List<Movie>> getAllMovies() {
        return this.localDB.movieDao().getAllMovies();
    }

    public void getAllMoviesByCategoryId(String categoryId, GetMovieItemListListener<Movie> listener) {
        this.executor.execute(() -> {
            List<Movie> movies = localDB.movieDao().getAllMoviesByCategoryId(categoryId);
            mainThreadHandler.post(() -> listener.onComplete(movies));
        });
    }

    /*public void getMovieById(Integer id, GetMovieItemListener<Movie> listener) {
        this.executor.execute(() -> {
            Movie movie = localDB.movieDao().getMovieById(id);
            mainThreadHandler.post(() -> listener.onComplete(movie));
        });
    }*/

    public void getMovieByName(String name, GetMovieItemListener<Movie> listener) {
        this.executor.execute(() -> {
            Movie movie = localDB.movieDao().getMovieByName(name);
            mainThreadHandler.post(() -> listener.onComplete(movie));
        });
    }

    public void addMovie(Movie movie) {
        try {
            this.localDB.movieDao().insertAll(movie);
        } catch (Exception e) {
            Log.d("TAG", e.getMessage());
        }
    }

    /*public void removeMovie(Integer index, ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            Movie deletedMovie = localDB.movieDao().getAllMovies().getValue().get(index);
            localDB.movieDao().delete(deletedMovie);
            mainThreadHandler.post(listener::onComplete);
        });
    }

    public void updateMovie(Integer index, Movie movie, ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            Movie deletedMovie = localDB.movieDao().getAllMovies().getValue().get(index);
            localDB.movieDao().delete(deletedMovie);
            localDB.movieDao().insertAll(movie);
            mainThreadHandler.post(listener::onComplete);
        });
    }*/
}
