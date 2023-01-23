package com.example.movieshare.repository;

import com.example.movieshare.repository.handlers.*;

public class Repository {
    public static MovieCommentHandler getMovieCommentHandler() {
        return MovieCommentHandler.instance();
    }

    public static MovieHandler getMovieHandler() {
        return MovieHandler.instance();
    }

    public static MovieCategoryHandler getMovieCategoryHandler() {
        return MovieCategoryHandler.instance();
    }
}
