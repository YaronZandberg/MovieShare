package com.example.movieshare.viewmodels.movie;

import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.repository.models.MovieCategory;

import java.util.ArrayList;
import java.util.List;

public class MovieProfileFragmentViewModel extends ViewModel {
    private List<Movie> movieList = new ArrayList<>();
    private Movie movie;
    private MovieCategory movieCategory;
    private String movieCategoryName;
    private Integer movieCategoryImage;

    public List<Movie> getMovieList() {
        return this.movieList;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    public Movie getMovie() {
        return this.movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public MovieCategory getMovieCategory() {
        return this.movieCategory;
    }

    public void setMovieCategory(MovieCategory movieCategory) {
        this.movieCategory = movieCategory;
    }

    public String getMovieCategoryName() {
        return this.movieCategoryName;
    }

    public void setMovieCategoryName(String movieCategoryName) {
        this.movieCategoryName = movieCategoryName;
    }

    public void setMovieCategoryImage(Integer movieCategoryImage) {
        this.movieCategoryImage = movieCategoryImage;
    }

    public Integer getMovieCategoryImage() {
        return movieCategoryImage;
    }
}
