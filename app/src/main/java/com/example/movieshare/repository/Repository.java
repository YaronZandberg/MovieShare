package com.example.movieshare.repository;

import static com.example.movieshare.enums.LoadingState.LOADING;
import static com.example.movieshare.enums.LoadingState.NOT_LOADING;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.movieshare.notifications.NotificationManager;
import com.example.movieshare.repository.firebase.FirebaseModel;
import com.example.movieshare.repository.models.MovieCategory;
import com.example.movieshare.repository.room.LocalModel;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {
    private static final Repository repositoryInstance = new Repository();
    private final LocalModel localModel = new LocalModel();
    private final FirebaseModel firebaseModel = new FirebaseModel();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private LiveData<List<MovieCategory>> movieCategories;

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

    public LiveData<List<MovieCategory>> getAllMovieCategories() {
        if (Objects.isNull(this.movieCategories)) {
            this.movieCategories = this.localModel.getMovieCategoryHandler().getAllMovieCategories();
            refreshAllMovieCategories();
        }
        return this.movieCategories;
    }

    public void refreshAllMovieCategories() {
        NotificationManager.instance().getEventMovieCategoryListLoadingState().setValue(LOADING);
        Long localLastUpdate = MovieCategory.getLocalLastUpdate();
        this.getFirebaseModel()
                .getAllMovieCategoriesSinceLastUpdate(localLastUpdate, movieCategories ->
                        this.executor.execute(() -> {
                            Log.d("TAG", " firebase return : " + movieCategories.size());
                            Long movieCategoryGlobalLastUpdate = localLastUpdate;
                            for (MovieCategory movieCategory : movieCategories) {
                                this.localModel.getMovieCategoryHandler().addMovieCategory(movieCategory);
                                if (movieCategoryGlobalLastUpdate < movieCategory.getCategoryLastUpdate()) {
                                    movieCategoryGlobalLastUpdate = movieCategory.getCategoryLastUpdate();
                                }
                            }
                            MovieCategory.setLocalLastUpdate(movieCategoryGlobalLastUpdate);
                            NotificationManager.instance()
                                    .getEventMovieCategoryListLoadingState().postValue(NOT_LOADING);
                        }));
    }
}
