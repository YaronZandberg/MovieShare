package com.example.movieshare.repository.models;

import static com.example.movieshare.constants.MovieConstants.*;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.movieshare.context.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

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
    @PrimaryKey
    @NonNull
    private String movieId;

    @ColumnInfo(index = true)
    @NonNull
    private String movieCategoryId;
    private String movieName;
    private String movieRating;
    private String description;
    private Long movieLastUpdate;
    private String imageUrl;

    public Movie(@NonNull String movieCategoryId, String movieName,
                 String movieRating, String description, String imageUrl) {
        this.movieCategoryId = movieCategoryId;
        this.movieName = movieName;
        this.movieRating = movieRating;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    @Ignore
    public Movie(@NonNull String movieId, @NonNull String movieCategoryId,
                 String movieName, String movieRating, String description, String imageUrl) {
        this.movieId = movieId;
        this.movieCategoryId = movieCategoryId;
        this.movieName = movieName;
        this.movieRating = movieRating;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    // TODO: handle exceptions from casting or null
    public static Movie fromJson(Map<String, Object> json) {
        String movieId = String.valueOf(json.get(MOVIE_ID));
        String movieCategoryId = String.valueOf(json.get(MOVIE_CATEGORY_ID));
        String movieName = String.valueOf(json.get(MOVIE_NAME));
        String movieRating = String.valueOf(json.get(MOVIE_RATING));
        String description = String.valueOf(json.get(MOVIE_DESCRIPTION));
        String imageUrl = String.valueOf(json.get(MOVIE_IMAGE_URL));
        Movie movie = new Movie(movieId, movieCategoryId, movieName, movieRating, description, imageUrl);
        Timestamp lastUpdate = (Timestamp) json.get(MOVIE_LAST_UPDATE);
        movie.setMovieLastUpdate(lastUpdate.getSeconds());
        return movie;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> movieJson = new HashMap<>();
        movieJson.put(MOVIE_ID, this.getMovieId());
        movieJson.put(MOVIE_CATEGORY_ID, this.getMovieCategoryId());
        movieJson.put(MOVIE_NAME, this.getMovieName());
        movieJson.put(MOVIE_RATING, this.getMovieRating());
        movieJson.put(MOVIE_DESCRIPTION, this.getDescription());
        movieJson.put(MOVIE_LAST_UPDATE, FieldValue.serverTimestamp());
        movieJson.put(MOVIE_IMAGE_URL, this.getImageUrl());
        return movieJson;
    }

    public static Long getLocalLastUpdate() {
        return MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong(MOVIE_LOCAL_LAST_UPDATE, 0);
    }

    public static void setLocalLastUpdate(Long localLastUpdate) {
        MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .edit().putLong(MOVIE_LOCAL_LAST_UPDATE, localLastUpdate).commit();
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

    public Long getMovieLastUpdate() {
        return this.movieLastUpdate;
    }

    public void setMovieLastUpdate(Long movieLastUpdate) {
        this.movieLastUpdate = movieLastUpdate;
    }

    public String getImageUrl() {
        return getImageUrl(false);
    }

    public String getImageUrl(Boolean fullPath) {
        if(fullPath) { return MOVIE_IMAGE_BASE_URL + this.imageUrl; }
        else { return this.imageUrl; }
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
