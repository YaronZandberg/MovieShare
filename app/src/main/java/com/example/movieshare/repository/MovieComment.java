package com.example.movieshare.repository;

public class MovieComment {
    private String serialId;
    private String userId;
    private String movieId;
    private String description;
    // TODO: Delete these two properties later because we will get them from Movie Entity using movieId FK
    private String movieName;
    private String movieRating;

    public MovieComment(String id, String userId, String movieId, String description,
                        String movieName, String movieRating) {
        this.serialId = id;
        this.userId = userId;
        this.movieId = movieId;
        this.description = description;
        this.movieName = movieName;
        this.movieRating = movieRating;
    }

    public String getSerialId() {
        return this.serialId;
    }

    public void setSerialId(String serialId) {
        this.serialId = serialId;
    }

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMovieId() {
        return this.movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
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
}
