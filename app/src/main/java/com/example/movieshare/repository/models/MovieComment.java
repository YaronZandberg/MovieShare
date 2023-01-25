package com.example.movieshare.repository.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MovieComment {
    @PrimaryKey
    @NonNull
    private Integer serialId;
    @NonNull
    private Integer userId;
    @NonNull
    private Integer movieId;
    private String description;
    // TODO: Delete these two properties later because we will get them from Movie Entity using movieId FK
    private String movieName;
    private String movieRating;

    public MovieComment(@NonNull Integer id, @NonNull Integer userId, @NonNull Integer movieId,
                        String description, String movieName, String movieRating) {
        this.serialId = id;
        this.userId = userId;
        this.movieId = movieId;
        this.description = description;
        this.movieName = movieName;
        this.movieRating = movieRating;
    }

    @NonNull
    public Integer getSerialId() {
        return this.serialId;
    }

    public void setSerialId(@NonNull Integer serialId) {
        this.serialId = serialId;
    }

    @NonNull
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(@NonNull Integer userId) {
        this.userId = userId;
    }

    @NonNull
    public Integer getMovieId() {
        return this.movieId;
    }

    public void setMovieId(@NonNull Integer movieId) {
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
