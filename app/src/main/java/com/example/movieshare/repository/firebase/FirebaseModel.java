package com.example.movieshare.repository.firebase;

import com.example.movieshare.listeners.*;
import com.example.movieshare.repository.firebase.executors.*;
import com.example.movieshare.repository.models.*;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirebaseModel {
    private final MovieCategoryExecutor movieCategoryExecutor = new MovieCategoryExecutor();
    private final MovieExecutor movieExecutor = new MovieExecutor();
    private final MovieCommentExecutor movieCommentExecutor = new MovieCommentExecutor();

    public FirebaseModel() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
    }

    // TODO: Need to call this function at first app loading and save results to ROOM.
    //  In addition, call this method after change from NotificationCenter of addMovieCategory
    //  in order to load delta again and update ROOM.
    public void getAllMovieCategories(GetMovieItemListListener<MovieCategory> listener) {
        this.movieCategoryExecutor.getAllMovieCategories(listener);
    }

    public void getAllMovieCategoriesSinceLastUpdate(Long localLastUpdate,
                                                     GetMovieItemListListener<MovieCategory> listener) {
        this.movieCategoryExecutor.getAllMovieCategoriesSinceLastUpdate(localLastUpdate, listener);
    }

    // TODO: Probably shouldn't be implemented by Firebase
    public void getMovieCategoryById(String id, GetMovieItemListener<MovieCategory> listener) {
        this.movieCategoryExecutor.getMovieCategoryById(id, listener);
    }

    public void addMovieCategory(MovieCategory movieCategory, ExecuteMovieItemListener listener) {
        this.movieCategoryExecutor.addMovieCategory(movieCategory, listener);
    }

    // TODO: There wasn't an original ROOM implementation
    public void removeMovieCategory(Integer index, ExecuteMovieItemListener listener) {

    }

    // TODO: There wasn't an original ROOM implementation
    public void updateMovieCategory(Integer index, MovieCategory movieCategory,
                                    ExecuteMovieItemListener listener) {

    }

    // TODO: Need to call this function at first app loading and save results to ROOM.
    //  In addition, call this method after change from NotificationCenter of addMovie
    //  in order to load delta again and update ROOM.
    public void getAllMovies(GetMovieItemListListener<Movie> listener) {
        this.movieExecutor.getAllMovies(listener);
    }

    // TODO: Probably shouldn't be implemented by Firebase
    public void getAllMoviesByCategoryId(String categoryId,
                                         GetMovieItemListListener<Movie> listener) {
        this.movieExecutor.getAllMoviesByCategoryId(categoryId, listener);
    }

    // TODO: There wasn't an original ROOM implementation
    public void getMovieById(Integer id, GetMovieItemListener<Movie> listener) {

    }

    // TODO: Probably shouldn't be implemented by Firebase
    public void getMovieByName(String name, GetMovieItemListener<Movie> listener) {
        this.movieExecutor.getMovieByName(name, listener);
    }

    public void addMovie(Movie movie, ExecuteMovieItemListener listener) {
        this.movieExecutor.addMovie(movie, listener);
    }

    // TODO: There wasn't an original ROOM implementation
    public void removeMovie(Integer index, ExecuteMovieItemListener listener) {

    }

    // TODO: There wasn't an original ROOM implementation
    public void updateMovie(Integer index, Movie movie, ExecuteMovieItemListener listener) {

    }

    // TODO: Need to call this function at first app loading and save results to ROOM.
    //  In addition, call this method after change from NotificationCenter of addMovieComment
    //  in order to load delta again and update ROOM.
    public void getAllMovieComments(GetMovieItemListListener<MovieComment> listener) {
        this.movieCommentExecutor.getAllMovieComments(listener);
    }

    // TODO: There wasn't an original ROOM implementation
    public void getMovieCommentById(Integer id, GetMovieItemListener<MovieComment> listener) {

    }

    // TODO: Probably shouldn't be implemented by Firebase
    public void getAllMovieCommentsByUserId(Integer userId,
                                            GetMovieItemListListener<MovieComment> listener){
        this.movieCommentExecutor.getAllMovieCommentsByUserId(userId, listener);
    }

    // TODO: Probably shouldn't be implemented by Firebase
    public void getAllMovieCommentsByMovieId(String movieId,
                                             GetMovieItemListListener<MovieComment> listener){
        this.movieCommentExecutor.getAllMovieCommentsByMovieId(movieId, listener);
    }

    public void addMovieComment(MovieComment movieComment, ExecuteMovieItemListener listener) {
        this.movieCommentExecutor.addMovieComment(movieComment, listener);
    }

    public void removeMovieComment(String movieCommentId, ExecuteMovieItemListener listener) {
        this.movieCommentExecutor.removeMovieComment(movieCommentId, listener);
    }

    public void updateMovieComment(String movieCommentId, MovieComment movieComment,
                                   ExecuteMovieItemListener listener) {
        this.movieCommentExecutor.updateMovieComment(movieCommentId, movieComment, listener);
    }
}
