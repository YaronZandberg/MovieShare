package com.example.movieshare.viewmodels.movie;

import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.repository.models.MovieCategory;

import java.util.ArrayList;
import java.util.List;

public class MovieListFragmentViewModel extends ViewModel {
    private List<Movie> movieList = new ArrayList<>();
    private List<MovieCategory> allMovieCategories = new ArrayList<>();
    private MovieCategory movieCategory;

    public List<Movie> getMovieList() {
        return this.movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public List<MovieCategory> getAllMovieCategories() {
        return this.allMovieCategories;
    }

    public void setAllMovieCategories(List<MovieCategory> allMovieCategories) {
        this.allMovieCategories = allMovieCategories;
    }

    public MovieCategory getMovieCategory() {
        return this.movieCategory;
    }

    public void setMovieCategory(MovieCategory movieCategory) {
        this.movieCategory = movieCategory;
    }
}
