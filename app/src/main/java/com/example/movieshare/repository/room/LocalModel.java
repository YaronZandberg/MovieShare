package com.example.movieshare.repository.room;

import com.example.movieshare.repository.room.handlers.*;

public class LocalModel {
    public MovieCategoryHandler getMovieCategoryHandler() {
        return MovieCategoryHandler.instance();
    }

    public MovieHandler getMovieHandler() {
        return MovieHandler.instance();
    }

    public MovieCommentHandler getMovieCommentHandler() {
        return MovieCommentHandler.instance();
    }
}
