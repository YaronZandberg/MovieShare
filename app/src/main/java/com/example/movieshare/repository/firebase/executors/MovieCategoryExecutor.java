package com.example.movieshare.repository.firebase.executors;

import static com.example.movieshare.constants.MovieCategoryConstants.*;

import com.example.movieshare.listeners.movies.*;
import com.example.movieshare.repository.models.MovieCategory;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MovieCategoryExecutor {
    private static final MovieCategoryExecutor movieCategoryExecutorInstance = new MovieCategoryExecutor();
    private final FirebaseFirestore db;

    private MovieCategoryExecutor() {
        this.db = FirebaseFirestore.getInstance();
    }

    public static MovieCategoryExecutor instance() {
        return movieCategoryExecutorInstance;
    }

    public void getAllMovieCategoriesSinceLastUpdate(Long localLastUpdate,
                                                     GetMovieItemListListener<MovieCategory> listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .whereGreaterThanOrEqualTo(MOVIE_CATEGORY_LAST_UPDATE,
                        new Timestamp(localLastUpdate, 0))
                .get()
                .addOnSuccessListener(task -> {
                    List<MovieCategory> movieCategories = new ArrayList<>();
                    for (DocumentSnapshot document : task.getDocuments()) {
                        MovieCategory movieCategory = MovieCategory.fromJson(document.getData());
                        movieCategory.setCategoryId(document.getId());
                        movieCategories.add(movieCategory);
                    }
                    listener.onComplete(movieCategories);
                });
    }

    public void getMovieCategoryByName(String name, GetMovieItemListener<MovieCategory> listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME).whereEqualTo(MOVIE_CATEGORY_NAME, name).get().addOnSuccessListener(task -> {
            MovieCategory movieCategory = null;
            List<DocumentSnapshot> jsonDocument = task.getDocuments();
            if(!jsonDocument.isEmpty()) {
                movieCategory = MovieCategory.fromJson(jsonDocument.get(0).getData());
                movieCategory.setCategoryId(jsonDocument.get(0).getId());
            }
            listener.onComplete(movieCategory);
        });
    }

    public void addMovieCategory(MovieCategory movieCategory, ExecuteMovieItemListener listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .add(movieCategory.toJson())
                .addOnSuccessListener(task -> listener.onComplete());
    }
}
