package com.example.movieshare.viewmodels.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.repository.models.MovieCategory;

import java.util.List;

public class MovieListFragmentViewModel extends ViewModel {
    private final LiveData<List<Movie>> movieList = Repository.getRepositoryInstance().getAllMovies();
    private final LiveData<List<MovieCategory>> allMovieCategories =
            Repository.getRepositoryInstance().getAllMovieCategories();
    private MovieCategory movieCategory;

    public LiveData<List<Movie>> getMovieList() {
        return this.movieList;
    }

    public LiveData<List<MovieCategory>> getAllMovieCategories() {
        return this.allMovieCategories;
    }

    public MovieCategory getMovieCategory() {
        return this.movieCategory;
    }

    public void setMovieCategory(MovieCategory movieCategory) {
        this.movieCategory = movieCategory;
    }
}
