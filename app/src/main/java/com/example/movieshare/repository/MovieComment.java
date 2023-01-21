package com.example.movieshare.repository;

public class MovieComment {
    private Integer serialId;
    private Integer userId;
    private Integer movieId;
    private String description;
    // TODO: Delete these two properties later because we will get them from Movie Entity using movieId FK
    private String movieName;
    private String movieRating;

    public MovieComment(Integer id, Integer userId, Integer movieId, String description,
                        String movieName, String movieRating) {
        this.serialId = id;
        this.userId = userId;
        this.movieId = movieId;
        this.description = description;
        this.movieName = movieName;
        this.movieRating = movieRating;
    }

    public Integer getSerialId() {
        return this.serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getMovieId() {
        return this.movieId;
    }

    public void setMovieId(Integer movieId) {
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
