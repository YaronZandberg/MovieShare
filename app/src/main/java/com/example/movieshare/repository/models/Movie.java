package com.example.movieshare.repository.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {@ForeignKey(entity = MovieCategory.class,
        parentColumns = "categoryId",
        childColumns = "movieCategoryId",
        onDelete = ForeignKey.CASCADE)
})
public class Movie {
    @PrimaryKey(autoGenerate = true)
    private Integer movieId;
    @ColumnInfo(index = true)
    private @NonNull Integer movieCategoryId;
    private String movieName;
    private String movieRating;
    private String description;

    public Movie(@NonNull Integer movieCategoryId, String movieName, String movieRating, String description) {
        this.movieCategoryId = movieCategoryId;
        this.movieName = movieName;
        this.movieRating = movieRating;
        this.description = description;
    }

    @NonNull
    public Integer getMovieId() {
        return this.movieId;
    }

    public void setMovieId(@NonNull Integer movieId) {
        this.movieId = movieId;
    }

    @NonNull
    public Integer getMovieCategoryId() {
        return this.movieCategoryId;
    }

    public void setMovieCategoryId(@NonNull Integer movieCategoryId) {
        this.movieCategoryId = movieCategoryId;
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
