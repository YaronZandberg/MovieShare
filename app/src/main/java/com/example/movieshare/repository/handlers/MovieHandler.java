package com.example.movieshare.repository.handlers;

import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import androidx.core.os.HandlerCompat;

import com.example.movieshare.context.MyApplication;
import com.example.movieshare.listeners.ExecuteMovieItemListener;
import com.example.movieshare.listeners.GetMovieByNameListener;
import com.example.movieshare.listeners.GetMovieItemListListener;
import com.example.movieshare.listeners.GetMovieItemListener;
import com.example.movieshare.repository.localdb.AppLocalDB;
import com.example.movieshare.repository.localdb.AppLocalDbRepository;
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

    public void getAllMovies(GetMovieItemListListener<Movie> listener) {
        this.executor.execute(() -> {
            List<Movie> movies = localDB.movieDao().getAllMovies();
            mainThreadHandler.post(() -> listener.onComplete(movies));
        });
    }

    public void getAllMoviesByCategoryId(Integer categoryId,
                                         GetMovieItemListListener<Movie> listener) {
        this.executor.execute(() -> {
            List<Movie> movies = localDB.movieDao().getAllMoviesByCategoryId(categoryId);
            mainThreadHandler.post(() -> listener.onComplete(movies));
        });
    }

    public void getMovieById(Integer id, GetMovieItemListener<Movie> listener) {
        this.executor.execute(() -> {
            Movie movie = localDB.movieDao().getMovieById(id);
            mainThreadHandler.post(() -> listener.onComplete(movie));
        });
    }

    public void getMovieByName(String name, GetMovieByNameListener listener) {
        this.executor.execute(() -> {
            Movie movie = localDB.movieDao().getMovieByName(name);
            mainThreadHandler.post(() -> {
                try {
                    listener.onComplete(movie);
                } catch (Exception e) {
                    Toast.makeText(MyApplication.getAppContext(), e.getMessage(), Toast.LENGTH_LONG)
                            .show();
                }
            });
        });
    }

    public void addMovie(Movie movie, ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            localDB.movieDao().insertAll(movie);
            mainThreadHandler.post(listener::onComplete);
        });
    }

    public void removeMovie(Integer index, ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            Movie deletedMovie = localDB.movieDao().getAllMovies().get(index);
            localDB.movieDao().delete(deletedMovie);
            mainThreadHandler.post(listener::onComplete);
        });
    }

    public void updateMovie(Integer index, Movie movie, ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            Movie deletedMovie = localDB.movieDao().getAllMovies().get(index);
            localDB.movieDao().delete(deletedMovie);
            localDB.movieDao().insertAll(movie);
            mainThreadHandler.post(listener::onComplete);
        });
    }
}
