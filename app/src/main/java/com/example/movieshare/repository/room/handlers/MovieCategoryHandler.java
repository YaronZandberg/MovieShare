package com.example.movieshare.repository.room.handlers;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.movieshare.listeners.ExecuteMovieItemListener;
import com.example.movieshare.listeners.GetMovieItemListListener;
import com.example.movieshare.listeners.GetMovieItemListener;
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

    public void getAllMovieCategories(GetMovieItemListListener<MovieCategory> listener) {
        this.executor.execute(() -> {
            List<MovieCategory> movieCategories = localDB.movieCategoryDao().getAllMovieCategories();
            mainThreadHandler.post(() -> listener.onComplete(movieCategories));
        });
    }

    public void getMovieCategoryById(String id, GetMovieItemListener<MovieCategory> listener) {
        this.executor.execute(() -> {
            MovieCategory movieCategory = localDB.movieCategoryDao().getMovieCategoryById(id);
            mainThreadHandler.post(() -> listener.onComplete(movieCategory));
        });
    }

    public void addMovieCategory(MovieCategory movieCategory, ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            localDB.movieCategoryDao().insertAll(movieCategory);
            mainThreadHandler.post(listener::onComplete);
        });
    }

    public void removeMovieCategory(Integer index, ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            MovieCategory deletedMovieCategory =
                    localDB.movieCategoryDao().getAllMovieCategories().get(index);
            localDB.movieCategoryDao().delete(deletedMovieCategory);
            mainThreadHandler.post(listener::onComplete);
        });
    }

    public void updateMovieCategory(Integer index, MovieCategory movieCategory,
                                    ExecuteMovieItemListener listener) {
        this.executor.execute(() -> {
            MovieCategory deletedMovieCategory =
                    localDB.movieCategoryDao().getAllMovieCategories().get(index);
            localDB.movieCategoryDao().delete(deletedMovieCategory);
            localDB.movieCategoryDao().insertAll(movieCategory);
            mainThreadHandler.post(listener::onComplete);
        });
    }
}
