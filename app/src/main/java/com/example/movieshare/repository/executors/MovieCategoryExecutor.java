package com.example.movieshare.repository.executors;

import static com.example.movieshare.constants.MovieCategoryConstants.*;

import com.example.movieshare.listeners.ExecuteMovieItemListener;
import com.example.movieshare.listeners.GetMovieItemListListener;
import com.example.movieshare.listeners.GetMovieItemListener;
import com.example.movieshare.repository.models.MovieCategory;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieCategoryExecutor {
    private static final MovieCategoryExecutor movieCategoryExecutorInstance = new MovieCategoryExecutor();
    private final FirebaseFirestore db;

    private MovieCategoryExecutor(){
        this.db = FirebaseFirestore.getInstance();
    }

    public static MovieCategoryExecutor instance() {
        return movieCategoryExecutorInstance;
    }

    public void getAllMovieCategories(GetMovieItemListListener<MovieCategory> listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<MovieCategory> movieCategories = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MovieCategory movieCategory = MovieCategory.fromJson(document.getData());
                            movieCategories.add(movieCategory);
                        }
                    }
                    listener.onComplete(movieCategories);
                });
    }

    // TODO: Check correctness of this method
    public void getMovieCategoryById(Integer id, GetMovieItemListener<MovieCategory> listener) {
        this.db.collection(MOVIE_CATEGORY_COLLECTION_NAME)
                .whereEqualTo(MOVIE_CATEGORY_ID, id)
                .get()
                .addOnCompleteListener(task -> {
                    MovieCategory movieCategory = null;
                    if (task.isSuccessful()) {
                        QuerySnapshot json = task.getResult();
                        List<DocumentSnapshot> jsonDocument = json.getDocuments();
                        movieCategory = MovieCategory.fromJson(jsonDocument.get(0).getData());
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
