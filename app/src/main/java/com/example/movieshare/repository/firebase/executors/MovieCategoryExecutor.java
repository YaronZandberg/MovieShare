package com.example.movieshare.repository.firebase.executors;

import static com.example.movieshare.constants.MovieCategoryConstants.*;

import com.example.movieshare.listeners.movies.*;
import com.example.movieshare.repository.models.MovieCategory;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

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

    // TODO: Need to call this function at first app loading and save results to ROOM.
    //  In addition, call this method after change from NotificationCenter of addMovieCategory
    //  in order to load delta again and update ROOM.
    /*public void getAllMovieCategories(GetMovieItemListListener<MovieCategory> listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<MovieCategory> movieCategories = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MovieCategory movieCategory = MovieCategory.fromJson(document.getData());
                            movieCategory.setCategoryId(document.getId());
                            movieCategories.add(movieCategory);
                        }
                    }
                    listener.onComplete(movieCategories);
                });
    }*/

    public void getAllMovieCategoriesSinceLastUpdate(Long localLastUpdate,
                                                     GetMovieItemListListener<MovieCategory> listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .whereGreaterThanOrEqualTo(MOVIE_CATEGORY_LAST_UPDATE,
                        new Timestamp(localLastUpdate, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<MovieCategory> movieCategories = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MovieCategory movieCategory = MovieCategory.fromJson(document.getData());
                            movieCategory.setCategoryId(document.getId());
                            movieCategories.add(movieCategory);
                        }
                    }
                    listener.onComplete(movieCategories);
                });
    }

    public void getMovieCategoryByName(String name, GetMovieItemListener<MovieCategory> listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .whereEqualTo(MOVIE_CATEGORY_NAME, name)
                .get()
                .addOnCompleteListener(task -> {
                    MovieCategory movieCategory = null;
                    if (task.isSuccessful()) {
                        QuerySnapshot json = task.getResult();
                        if(!json.isEmpty()) {
                            List<DocumentSnapshot> jsonDocument = json.getDocuments();
                            movieCategory = MovieCategory.fromJson(jsonDocument.get(0).getData());
                            movieCategory.setCategoryId(jsonDocument.get(0).getId());
                        }
                    }
                    listener.onComplete(movieCategory);
                });
    }

    // TODO: I'm not sure that we need to implement this with firebase,
    //  because this function will be called in front of ROOM.
    /*public void getMovieCategoryById(String id, GetMovieItemListener<MovieCategory> listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .whereEqualTo(FieldPath.documentId(), id)
                .get()
                .addOnCompleteListener(task -> {
                    MovieCategory movieCategory = null;
                    if (task.isSuccessful()) {
                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                        if(!queryDocumentSnapshots.getDocuments().isEmpty()) {
                            DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                            movieCategory = MovieCategory.fromJson(documentSnapshot.getData());
                            movieCategory.setCategoryId(documentSnapshot.getId());
                        }
                    }
                    listener.onComplete(movieCategory);
                });
    }*/

    public void addMovieCategory(MovieCategory movieCategory, ExecuteMovieItemListener listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .add(movieCategory.toJson())
                .addOnCompleteListener(task -> listener.onComplete());
    }

    // TODO: There wasn't an original ROOM implementation
    /*public void removeMovieCategory(Integer index, ExecuteMovieItemListener listener) {

    }*/

    // TODO: There wasn't an original ROOM implementation
    /*public void updateMovieCategory(Integer index, MovieCategory movieCategory,
                                    ExecuteMovieItemListener listener) {

    }*/
}
