package com.example.movieshare.repository.firebase.executors;

import static com.example.movieshare.constants.MovieConstants.*;

import android.util.Log;

import com.example.movieshare.listeners.movies.*;
import com.example.movieshare.repository.models.Movie;

import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class MovieExecutor {
    private static final MovieExecutor movieExecutorInstance = new MovieExecutor();
    private final FirebaseFirestore db;

    private MovieExecutor() {
        this.db = FirebaseFirestore.getInstance();
    }

    public static MovieExecutor instance() {
        return movieExecutorInstance;
    }

    public void getAllMoviesSinceLastUpdate(Long localLastUpdate,
                                            GetMovieItemListListener<Movie> listener) {
        this.db.collection(MOVIE_COLLECTION_NAME)
                .whereGreaterThanOrEqualTo(MOVIE_LAST_UPDATE,
                        new Timestamp(localLastUpdate, 0))
                .get()
                .addOnSuccessListener(task -> {
                    List<Movie> movies = new ArrayList<>();
                    for (DocumentSnapshot document : task.getDocuments()) {
                        Movie movie = Movie.fromJson(document.getData());
                        movie.setMovieId(document.getId());
                        movies.add(movie);
                    }
                    listener.onComplete(movies);
                }).addOnFailureListener(task -> Log.d("Error", task.getMessage()));
    }

    // TODO: I'm not sure that we need to implement this with firebase,
    //  because this function will be called in front of ROOM.
    public void getMovieByName(String name, GetMovieItemListListener<Movie> listener) {
        this.db.collection(MOVIE_COLLECTION_NAME)
                .whereEqualTo(MOVIE_NAME, name)
                .get()
                .addOnSuccessListener(task -> {
                    List<Movie> movies = new ArrayList<>();
                    for (DocumentSnapshot document : task.getDocuments()) {
                        Movie movie = Movie.fromJson(document.getData());
                        movie.setMovieId(document.getId());
                        movies.add(movie);
                    }
                    listener.onComplete(movies);
                });
    }

    public void addMovie(Movie movie, ExecuteMovieItemListener listener) {
        this.db.collection(MOVIE_COLLECTION_NAME)
                .add(movie.toJson())
                .addOnSuccessListener(task -> listener.onComplete());
    }
}
