package com.example.movieshare.repository.models;

public class Movie {
    private Integer movieId;
    private String movieName;
    private String movieRating;
    private String description;

    public Movie(Integer movieId, String movieName, String movieRating, String description) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieRating = movieRating;
        this.description = description;
    }

    public Integer getMovieId() {
        return this.movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieRating() {
        return this.movieRating;
    }

    public void setMovieRating(String movieRating) {
        this.movieRating = movieRating;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
