package com.example.movieshare.repository.handlers;

import com.example.movieshare.repository.models.MovieCategory;

import java.util.ArrayList;
import java.util.List;

public class MovieCategoryHandler {
    private final Integer MOVIE_CATEGORIES_AMOUNT = 15;
    private final List<MovieCategory> movieCategoryList = new ArrayList();
    private static final MovieCategoryHandler movieCategoryHandlerInstance = new MovieCategoryHandler();

    private MovieCategoryHandler() {
        for (int i = 0; i < this.MOVIE_CATEGORIES_AMOUNT; i++) {
            this.addMovieCategory(initializeMovieCategory(i));
        }
    }

    public static MovieCategoryHandler instance() {
        return movieCategoryHandlerInstance;
    }

    private MovieCategory initializeMovieCategory(Integer index) {
        Integer categoryId = index;
        String categoryName = "name " + index;
        String categoryRating = "rating " + index;
        String description = "description " + index;
        return new MovieCategory(categoryId, categoryName, categoryRating, description);
    }

    public List<MovieCategory> getAllMovieCategories() {
        return movieCategoryList;
    }

    public MovieCategory getMovieCategoryById(Integer id) {
        return movieCategoryList.get(id);
    }

    public void addMovieCategory(MovieCategory movieCategory) {
        movieCategoryList.add(movieCategory);
    }

    public void setMovieCategory(Integer index, MovieCategory movieCategory) {
        movieCategoryList.set(index, movieCategory);
    }

    public void removeMovieCategory(Integer index) {
        movieCategoryList.remove(movieCategoryList.get(index));
    }
}
