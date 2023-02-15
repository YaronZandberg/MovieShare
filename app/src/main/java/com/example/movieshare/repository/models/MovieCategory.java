package com.example.movieshare.repository.models;

import static com.example.movieshare.constants.MovieCategoryConstants.*;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.example.movieshare.context.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity(indices = {@Index(value = {"categoryName"}, unique = true)})
public class MovieCategory {
    @PrimaryKey
    @NonNull
    private String categoryId;
    private String categoryName;
    private String categoryRating;
    private String description;
    private Long categoryLastUpdate;

    public MovieCategory(String categoryName, String categoryRating, String description) {
        this.categoryName = categoryName;
        this.categoryRating = categoryRating;
        this.description = description;
    }

    @Ignore
    public MovieCategory(@NonNull String categoryId, String categoryName,
                         String categoryRating, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryRating = categoryRating;
        this.description = description;
    }

    public static MovieCategory fromJson(Map<String, Object> json) {
        try {
            String categoryId = String.valueOf(json.get(MOVIE_CATEGORY_ID));
            String categoryName = String.valueOf(json.get(MOVIE_CATEGORY_NAME));
            String categoryRating = String.valueOf(json.get(MOVIE_CATEGORY_RATING));
            String description = String.valueOf(json.get(MOVIE_CATEGORY_DESCRIPTION));
            MovieCategory movieCategory =
                    new MovieCategory(categoryId, categoryName, categoryRating, description);
            Timestamp lastUpdate = (Timestamp) json.get(MOVIE_CATEGORY_LAST_UPDATE);
            movieCategory.setCategoryLastUpdate(lastUpdate.getSeconds());
            return movieCategory;
        } catch (Exception e) {
            return null;
        }
    }

    public Map<String, Object> toJson() {
        Map<String, Object> movieCategoryJson = new HashMap<>();
        movieCategoryJson.put(MOVIE_CATEGORY_ID, this.getCategoryId());
        movieCategoryJson.put(MOVIE_CATEGORY_NAME, this.getCategoryName());
        movieCategoryJson.put(MOVIE_CATEGORY_RATING, this.getCategoryRating());
        movieCategoryJson.put(MOVIE_CATEGORY_DESCRIPTION, this.getDescription());
        movieCategoryJson.put(MOVIE_CATEGORY_LAST_UPDATE, FieldValue.serverTimestamp());
        return movieCategoryJson;
    }

    public static Long getLocalLastUpdate() {
        return MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong(MOVIE_CATEGORY_LOCAL_LAST_UPDATE, 0);
    }

    public static void setLocalLastUpdate(Long localLastUpdate) {
        MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .edit().putLong(MOVIE_CATEGORY_LOCAL_LAST_UPDATE, localLastUpdate).commit();
    }

    @NonNull
    public String getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(@NonNull String categoryId) {
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

    public Long getCategoryLastUpdate() {
        return this.categoryLastUpdate;
    }

    public void setCategoryLastUpdate(Long categoryLastUpdate) {
        this.categoryLastUpdate = categoryLastUpdate;
    }
}
