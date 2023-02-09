package com.example.movieshare.repository.room;

import androidx.lifecycle.LiveData;

import com.example.movieshare.repository.models.MovieCategory;
import com.example.movieshare.repository.room.handlers.*;

import java.util.List;

public class LocalModel {
    public MovieCommentHandler getMovieCommentHandler() {
        return MovieCommentHandler.instance();
    }

    public MovieHandler getMovieHandler() {
        return MovieHandler.instance();
    }

    /*public MovieCategoryHandler getMovieCategoryHandler() {
        return MovieCategoryHandler.instance();
    }*/

    private final MovieCategoryHandler movieCategoryHandler = new MovieCategoryHandler();

    public LiveData<List<MovieCategory>> getAllMovieCategories() {
        return this.movieCategoryHandler.getAllMovieCategories();
    }

    public void addMovieCategory(MovieCategory movieCategory) {
        this.movieCategoryHandler.addMovieCategory(movieCategory);
    }
}
