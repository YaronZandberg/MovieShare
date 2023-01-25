package com.example.movieshare.listeners;

import com.example.movieshare.repository.models.MovieComment;

public interface GetMovieCommentByIdListener {
    void onComplete(MovieComment movieComment);
}
