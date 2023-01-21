package com.example.movieshare.repository.handlers;

import com.example.movieshare.repository.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MovieHandler {
    private final Integer MOVIES_AMOUNT = 15;
    private final List<Movie> movieList = new ArrayList();
    private static final MovieHandler movieHandlerInstance = new MovieHandler();

    private MovieHandler() {
        for (int i = 0; i < this.MOVIES_AMOUNT; i++) {
            this.addMovie(initializeMovie(i));
        }
    }

    public static MovieHandler instance() {
        return movieHandlerInstance;
    }

    private Movie initializeMovie(Integer index) {
        Integer movieId = index;
        String movieName = "name " + index;
        String movieRating = "rating " + index;
        String description = "description " + index;
        return new Movie(movieId, movieName, movieRating, description);
    }

    public List<Movie> getAllMovies() {
        return movieList;
    }

    public Movie getMovieById(Integer id) {
        return movieList.get(id);
    }

    public void addMovie(Movie movie) {
        movieList.add(movie);
    }

    public void setMovie(Integer index, Movie movie) {
        movieList.set(index, movie);
    }

    public void removeMovie(Integer index) {
        movieList.remove(movieList.get(index));
    }
}
