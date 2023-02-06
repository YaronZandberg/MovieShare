package com.example.movieshare.repository.firebase.executors;

import static com.example.movieshare.constants.MovieCategoryConstants.*;

import com.example.movieshare.listeners.ExecuteMovieItemListener;
import com.example.movieshare.listeners.GetMovieItemListListener;
import com.example.movieshare.listeners.GetMovieItemListener;
import com.example.movieshare.repository.models.MovieCategory;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieCategoryExecutor {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MovieCategoryExecutor() {
    }

    public void getAllMovieCategories(GetMovieItemListListener<MovieCategory> listener) {
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
    }

    // TODO: I'm not sure that we need to implement this with firebase,
    //  because this function will be called in front of ROOM.
    public void getMovieCategoryById(String id, GetMovieItemListener<MovieCategory> listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .whereEqualTo(FieldPath.documentId(), id)
                .get()
                .addOnCompleteListener(task -> {
                    MovieCategory movieCategory = null;
                    if (task.isSuccessful()) {
                        QuerySnapshot queryDocumentSnapshots = task.getResult();
                        DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                        movieCategory = MovieCategory.fromJson(documentSnapshot.getData());
                    }
                    listener.onComplete(movieCategory);
                });
    }

    public void addMovieCategory(MovieCategory movieCategory, ExecuteMovieItemListener listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .add(movieCategory.toJson())
                .addOnCompleteListener(task -> listener.onComplete());
    }

    public void removeMovieCategory(Integer index, ExecuteMovieItemListener listener) {

    }

    public void updateMovieCategory(Integer index, MovieCategory movieCategory,
                                    ExecuteMovieItemListener listener) {

    }
}
