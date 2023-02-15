package com.example.movieshare.repository.room.handlers;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.example.movieshare.listeners.movies.*;
import com.example.movieshare.repository.room.localdb.AppLocalDB;
import com.example.movieshare.repository.room.localdb.AppLocalDbRepository;
import com.example.movieshare.repository.models.MovieCategory;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MovieCategoryHandler {
    private static final MovieCategoryHandler movieCategoryHandlerInstance = new MovieCategoryHandler();
    private final Executor executor;
    private final Handler mainThreadHandler;
    private final AppLocalDbRepository localDB;

    private MovieCategoryHandler() {
        this.executor = Executors.newSingleThreadExecutor();
        this.mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        this.localDB = AppLocalDB.getAppDB();
    }

    public static MovieCategoryHandler instance() {
        return movieCategoryHandlerInstance;
    }

    public LiveData<List<MovieCategory>> getAllMovieCategories() {
        return localDB.movieCategoryDao().getAllMovieCategories();
    }

    public void getMovieCategoryById(String id, GetMovieItemListener<MovieCategory> listener) {
        this.executor.execute(() -> {
            MovieCategory movieCategory = localDB.movieCategoryDao().getMovieCategoryById(id);
            mainThreadHandler.post(() -> listener.onComplete(movieCategory));
        });
    }

    public void addMovieCategory(MovieCategory movieCategory) {
        try {
            localDB.movieCategoryDao().insertAll(movieCategory);
        } catch (Exception e) {
            Log.d("TAG", e.getMessage());
        }
    }
}
