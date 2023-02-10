package com.example.movieshare.repository.firebase;

import com.example.movieshare.repository.firebase.executors.*;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;

public class FirebaseModel {
    public FirebaseModel() {
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.setFirestoreSettings(settings);
    }

    public MovieCategoryExecutor getMovieCategoryExecutor() {
        return MovieCategoryExecutor.instance();
    }

    public MovieExecutor getMovieExecutor() {
        return MovieExecutor.instance();
    }

    public MovieCommentExecutor getMovieCommentExecutor() {
        return MovieCommentExecutor.instance();
    }
}
