package com.example.movieshare.viewmodels.movie;

import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.models.MovieCategory;

import java.util.ArrayList;
import java.util.List;

public class MovieHomeFragmentViewModel extends ViewModel {
    private List<MovieCategory> movieCategories = new ArrayList<>();

    public List<MovieCategory> getMovieCategories() {
        return this.movieCategories;
    }

    public void setMovieCategories(List<MovieCategory> movieCategories) {
        this.movieCategories = movieCategories;
    }
}
