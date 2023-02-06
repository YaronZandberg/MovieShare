package com.example.movieshare.repository.models;

import static com.example.movieshare.constants.MovieConstants.*;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity(foreignKeys = {
        @ForeignKey(entity = MovieCategory.class,
                parentColumns = "categoryId",
                childColumns = "movieCategoryId",
                onDelete = ForeignKey.CASCADE)},
        indices = {@Index(value = {"movieName"}, unique = true)}
)
public class Movie {
    @PrimaryKey @NonNull
    private String movieId;

    @ColumnInfo(index = true)
    @NonNull
    private String movieCategoryId;
    private String movieName;
    private String movieRating;
    private String description;

    public Movie(@NonNull String movieCategoryId, String movieName,
                 String movieRating, String description) {
        this.movieCategoryId = movieCategoryId;
        this.movieName = movieName;
        this.movieRating = movieRating;
        this.description = description;
    }

    @Ignore
    public Movie(@NonNull String movieId, @NonNull String movieCategoryId,
                 String movieName, String movieRating, String description) {
        this.movieId = movieId;
        this.movieCategoryId = movieCategoryId;
        this.movieName = movieName;
        this.movieRating = movieRating;
        this.description = description;
    }

    // TODO: handle exceptions from casting or null
    public static Movie fromJson(Map<String, Object> json) {
        String movieId = String.valueOf(json.get(MOVIE_ID));
        String movieCategoryId = String.valueOf(json.get(MOVIE_CATEGORY_ID));
        String movieName = String.valueOf(json.get(MOVIE_NAME));
        String movieRating = String.valueOf(json.get(MOVIE_RATING));
        String description = String.valueOf(json.get(MOVIE_DESCRIPTION));
        return new Movie(movieId, movieCategoryId, movieName, movieRating, description);
    }

    public Map<String, Object> toJson() {
        Map<String, Object> movieJson = new HashMap<>();
        movieJson.put(MOVIE_ID, this.getMovieId());
        movieJson.put(MOVIE_CATEGORY_ID, this.getMovieCategoryId());
        movieJson.put(MOVIE_NAME, this.getMovieName());
        movieJson.put(MOVIE_RATING, this.getMovieRating());
        movieJson.put(MOVIE_DESCRIPTION, this.getDescription());
        return movieJson;
    }

    @NonNull
    public String getMovieId() {
        return this.movieId;
    }

    public void setMovieId(@NonNull String movieId) {
        this.movieId = movieId;
    }

    @NonNull
    public String getMovieCategoryId() {
        return this.movieCategoryId;
    }

    public void setMovieCategoryId(@NonNull String movieCategoryId) {
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
