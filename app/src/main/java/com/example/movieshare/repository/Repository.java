package com.example.movieshare.repository;

import static com.example.movieshare.enums.LoadingState.*;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.example.movieshare.listeners.authentication.AddUserListener;
import com.example.movieshare.notifications.NotificationManager;
import com.example.movieshare.repository.firebase.AuthModel;
import com.example.movieshare.repository.firebase.FirebaseModel;
import com.example.movieshare.repository.models.*;
import com.example.movieshare.repository.room.LocalModel;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {
    private static final Repository repositoryInstance = new Repository();
    private final LocalModel localModel = new LocalModel();
    private final FirebaseModel firebaseModel = new FirebaseModel();
    private final AuthModel authModel = new AuthModel();
    private final Executor executor = Executors.newSingleThreadExecutor();
    private final Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
    private LiveData<List<MovieCategory>> movieCategories;
    private LiveData<List<Movie>> movies;
    private LiveData<List<MovieComment>> movieComments;

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

    public AuthModel getAuthModel() {
        return this.authModel;
    }

    public Executor getExecutor() {
        return this.executor;
    }

    public Handler getMainThreadHandler() {
        return this.mainThreadHandler;
    }

    public LiveData<List<MovieCategory>> getAllMovieCategories() {
        if (Objects.isNull(this.movieCategories)) {
            this.movieCategories = this.localModel.getMovieCategoryHandler().getAllMovieCategories();
            refreshAllMovieCategories();
        }
        return this.movieCategories;
    }

    public LiveData<List<Movie>> getAllMovies() {
        if (Objects.isNull(this.movies)) {
            this.movies = this.localModel.getMovieHandler().getAllMovies();
            refreshAllMovies();
        }
        return this.movies;
    }

    public LiveData<List<MovieComment>> getAllMovieComments() {
        if (Objects.isNull(this.movieComments)) {
            this.movieComments = this.localModel.getMovieCommentHandler().getAllMovieComments();
            refreshAllMovieComments();
        }
        return this.movieComments;
    }

    public void refreshAllMovieCategories() {
        NotificationManager.instance().getEventMovieCategoryListLoadingState().setValue(LOADING);
        Long localLastUpdate = MovieCategory.getLocalLastUpdate();
        this.getFirebaseModel().getMovieCategoryExecutor()
                .getAllMovieCategoriesSinceLastUpdate(localLastUpdate, movieCategories ->
                        this.executor.execute(() -> {
                            Log.d("TAG", "MovieCategory: firebase return : " + movieCategories.size());
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

    public void refreshAllMovies() {
        NotificationManager.instance().getEventMovieListLoadingState().setValue(LOADING);
        Long localLastUpdate = Movie.getLocalLastUpdate();
        this.getFirebaseModel().getMovieExecutor()
                .getAllMoviesSinceLastUpdate(localLastUpdate, movies ->
                        this.executor.execute(() -> {
                            Log.d("TAG", "Movie: firebase return : " + movies.size());
                            Long movieGlobalLastUpdate = localLastUpdate;
                            for (Movie movie : movies) {
                                this.localModel.getMovieHandler().addMovie(movie);
                                if (movieGlobalLastUpdate < movie.getMovieLastUpdate()) {
                                    movieGlobalLastUpdate = movie.getMovieLastUpdate();
                                }
                            }
                            Movie.setLocalLastUpdate(movieGlobalLastUpdate);
                            NotificationManager.instance()
                                    .getEventMovieListLoadingState().postValue(NOT_LOADING);
                        }));
    }

    public void refreshAllMovieComments() {
        NotificationManager.instance().getEventMovieCommentListLoadingState().setValue(LOADING);
        Long localLastUpdate = MovieComment.getLocalLastUpdate();
        this.getFirebaseModel().getMovieCommentExecutor()
                .getAllMovieCommentsSinceLastUpdate(localLastUpdate, movieComments ->
                        this.executor.execute(() -> {
                            Log.d("TAG", "MovieComment: firebase return : " + movieComments.size());
                            Long movieCommentGlobalLastUpdate = localLastUpdate;
                            for (MovieComment movieComment : movieComments) {
                                this.localModel.getMovieCommentHandler().addMovieComment(movieComment);
                                if (movieCommentGlobalLastUpdate < movieComment.getMovieCommentLastUpdate()) {
                                    movieCommentGlobalLastUpdate = movieComment.getMovieCommentLastUpdate();
                                }
                            }
                            MovieComment.setLocalLastUpdate(movieCommentGlobalLastUpdate);
                            NotificationManager.instance()
                                    .getEventMovieCommentListLoadingState().postValue(NOT_LOADING);
                        }));
    }

    public void register(AddUserListener addUserListener, User user, String password) {
        this.authModel.register(user.getEmail(), password, uid -> {
            user.setUserId(uid);
            this.firebaseModel.getUserExecutor().addUser(user, addUserListener);
        });
    }
}
