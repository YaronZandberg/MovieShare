package com.example.movieshare.repository.room;

import com.example.movieshare.repository.room.handlers.*;

public class LocalModel {
    public MovieCommentHandler getMovieCommentHandler() {
        return MovieCommentHandler.instance();
    }

    public MovieHandler getMovieHandler() {
        return MovieHandler.instance();
    }

    public MovieCategoryHandler getMovieCategoryHandler() {
        return MovieCategoryHandler.instance();
    }
}
