package com.example.movieshare.repository.models;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class MovieCategory {
    @PrimaryKey
    @NonNull
    private Integer categoryId;
    private String categoryName;
    private String categoryRating;
    private String description;

    public MovieCategory(@NonNull Integer categoryId, String categoryName,
                         String categoryRating, String description) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.categoryRating = categoryRating;
        this.description = description;
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
