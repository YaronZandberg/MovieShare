package com.example.movieshare.repository;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;

import com.example.movieshare.listeners.GetMovieItemListListener;
import com.example.movieshare.repository.firebase.FirebaseModel;
import com.example.movieshare.repository.models.MovieCategory;
import com.example.movieshare.repository.room.LocalModel;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {
    public static final Repository repositoryInstance = new Repository();
    private final LocalModel localModel = new LocalModel();
    private final FirebaseModel firebaseModel = new FirebaseModel();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    private Repository() {
    }

    public static Repository getRepositoryInstance() {
        return repositoryInstance;
    }

    public LocalModel getLocalModel() {
        return this.localModel;
    }

    public FirebaseModel getFirebaseModel() {
        return this.firebaseModel;
    }

    public void getAllMovieCategories(GetMovieItemListListener<MovieCategory> listener) {
        Long localLastUpdate = MovieCategory.getLocalLastUpdate();
        this.getFirebaseModel()
                .getAllMovieCategoriesSinceLastUpdate(localLastUpdate, movieCategories -> {
                    this.executor.execute(() -> {
                        Long movieCategoryGlobalLastUpdate = localLastUpdate;
                        for (MovieCategory movieCategory : movieCategories) {
                            this.getLocalModel().getMovieCategoryHandler().addMovieCategory(movieCategory);
                            if (movieCategoryGlobalLastUpdate < movieCategory.getCategoryLastUpdate()) {
                                movieCategoryGlobalLastUpdate = movieCategory.getCategoryLastUpdate();
                            }
                        }
                        MovieCategory.setLocalLastUpdate(movieCategoryGlobalLastUpdate);
                        List<MovieCategory> updatedMovieCategories = this.localModel.getMovieCategoryHandler().getAllMovieCategories();
                        mainThreadHandler.post(() -> listener.onComplete(updatedMovieCategories));
                    });
        });
    }
}
