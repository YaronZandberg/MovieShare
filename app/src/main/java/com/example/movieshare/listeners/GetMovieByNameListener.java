package com.example.movieshare.listeners;

import com.example.movieshare.repository.models.Movie;

public interface GetMovieByNameListener {
    void onComplete(Movie movie) throws Exception;
}
