package com.example.movieshare.repository.models;

import static com.example.movieshare.constants.MovieCategoryConstants.*;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

@Entity(indices = {@Index(value = {"categoryName"}, unique = true)})
public class MovieCategory {
    @PrimaryKey(autoGenerate = true)
    private Integer categoryId;
    private String categoryName;
    private String categoryRating;
    private String description;

    public MovieCategory(String categoryName, String categoryRating, String description) {
        this.categoryName = categoryName;
        this.categoryRating = categoryRating;
        this.description = description;
    }

    public MovieCategory(Integer categoryId, String categoryName,
                         String categoryRating, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryRating = categoryRating;
        this.description = description;
    }

    // TODO: handle exceptions from casting or null
    public static MovieCategory fromJson(Map<String, Object> json) {
        Integer categoryId = Integer.parseInt(String.valueOf(json.get(MOVIE_CATEGORY_ID)));
        String categoryName = String.valueOf(json.get(MOVIE_CATEGORY_NAME));
        String categoryRating = String.valueOf(json.get(MOVIE_CATEGORY_RATING));
        String description = String.valueOf(json.get(MOVIE_CATEGORY_DESCRIPTION));
        return new MovieCategory(categoryId, categoryName, categoryRating, description);
    }

    public Map<String, Object> toJson() {
        Map<String, Object> movieCategoryJson = new HashMap<>();
        movieCategoryJson.put(MOVIE_CATEGORY_ID, this.getCategoryId());
        movieCategoryJson.put(MOVIE_CATEGORY_NAME, this.getCategoryName());
        movieCategoryJson.put(MOVIE_CATEGORY_RATING, this.getCategoryRating());
        movieCategoryJson.put(MOVIE_CATEGORY_DESCRIPTION, this.getDescription());
        return movieCategoryJson;
    }

    @NonNull
    public Integer getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(@NonNull Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryRating() {
        return this.categoryRating;
    }

    public void setCategoryRating(String categoryRating) {
        this.categoryRating = categoryRating;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
