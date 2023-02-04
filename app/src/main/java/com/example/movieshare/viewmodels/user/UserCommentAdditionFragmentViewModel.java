package com.example.movieshare.viewmodels.user;

import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.models.Movie;

public class UserCommentAdditionFragmentViewModel extends ViewModel {
    private Movie movie;

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }
}
