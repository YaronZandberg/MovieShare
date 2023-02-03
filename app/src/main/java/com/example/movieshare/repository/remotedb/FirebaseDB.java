package com.example.movieshare.repository.remotedb;

import com.example.movieshare.listeners.*;
import com.example.movieshare.repository.executors.*;
import com.example.movieshare.repository.models.*;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirebaseDB {
    private final FirebaseFirestore db;
    private final MovieCategoryExecutor movieCategoryExecutor;
    private final MovieExecutor movieExecutor;
    private final MovieCommentExecutor movieCommentExecutor;

    public FirebaseDB() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        this.db = FirebaseFirestore.getInstance();
        this.db.setFirestoreSettings(settings);
        this.movieCategoryExecutor = MovieCategoryExecutor.instance();
        this.movieExecutor = MovieExecutor.instance();
        this.movieCommentExecutor = MovieCommentExecutor.instance();
    }

    public void getAllMovieCategories(GetMovieItemListListener<MovieCategory> listener) {
        this.movieCategoryExecutor.getAllMovieCategories(listener);
    }

    public void getMovieCategoryById(Integer id, GetMovieItemListener<MovieCategory> listener) {
        this.movieCategoryExecutor.getMovieCategoryById(id, listener);
    }

    public void addMovieCategory(MovieCategory movieCategory, ExecuteMovieItemListener listener) {
        this.movieCategoryExecutor.addMovieCategory(movieCategory, listener);
    }

    public void removeMovieCategory(Integer index, ExecuteMovieItemListener listener) {

    }

    public void updateMovieCategory(Integer index, MovieCategory movieCategory,
                                    ExecuteMovieItemListener listener) {

    }

    public void getAllMovies(GetMovieItemListListener<Movie> listener) {
        this.movieExecutor.getAllMovies(listener);
    }

    public void getAllMoviesByCategoryId(Integer categoryId,
                                         GetMovieItemListListener<Movie> listener) {
        this.movieExecutor.getAllMoviesByCategoryId(categoryId, listener);
    }

    public void getMovieById(Integer id, GetMovieItemListener<Movie> listener) {

    }

    public void getMovieByName(String name, GetMovieByNameListener listener) {
        this.movieExecutor.getMovieByName(name, listener);
    }

    public void addMovie(Movie movie, ExecuteMovieItemListener listener) {
        this.movieExecutor.addMovie(movie, listener);
    }

    public void removeMovie(Integer index, ExecuteMovieItemListener listener) {

    }

    public void updateMovie(Integer index, Movie movie, ExecuteMovieItemListener listener) {

    }

    public void getAllMovieComments(GetMovieItemListListener<MovieComment> listener) {
        this.movieCommentExecutor.getAllMovieComments(listener);
    }

    public void getMovieCommentById(Integer id, GetMovieItemListener<MovieComment> listener) {

    }

    public void getAllMovieCommentsByUserId(Integer userId,
                                            GetMovieItemListListener<MovieComment> listener){
        this.movieCommentExecutor.getAllMovieCommentsByUserId(userId, listener);
    }

    public void getAllMovieCommentsByMovieId(Integer movieId,
                                             GetMovieItemListListener<MovieComment> listener){
        this.movieCommentExecutor.getAllMovieCommentsByMovieId(movieId, listener);
    }

    public void addMovieComment(MovieComment movieComment, ExecuteMovieItemListener listener) {
        this.movieCommentExecutor.addMovieComment(movieComment, listener);
    }

    public void removeMovieComment(Integer index, ExecuteMovieItemListener listener) {
        this.movieCommentExecutor.removeMovieComment(index, listener);
    }

    public void updateMovieComment(Integer index, MovieComment movieComment,
                                   ExecuteMovieItemListener listener) {
        this.movieCommentExecutor.updateMovieComment(index, movieComment, listener);
    }
}
