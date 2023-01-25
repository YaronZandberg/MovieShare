package com.example.movieshare.listeners;

import com.example.movieshare.repository.models.MovieComment;

import java.util.List;

public interface GetAllMovieCommentsListener {
    void onComplete(List<MovieComment> movieCommentList);
}
