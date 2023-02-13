package com.example.movieshare.repository.models;

import java.util.List;

public class CategoriesApi {
    List<CategoryApi> genres;

    public void setGenres(List<CategoryApi> genres) {
        this.genres = genres;
    }

    public List<CategoryApi> getGenres() {
        return genres;
    }
}
