package com.example.movieshare.repository.firebase.executors;

import static com.example.movieshare.constants.MovieConstants.*;

import com.example.movieshare.listeners.ExecuteMovieItemListener;
import com.example.movieshare.listeners.GetMovieItemListListener;
import com.example.movieshare.listeners.GetMovieItemListener;
import com.example.movieshare.repository.models.Movie;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieExecutor {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MovieExecutor() {
    }

    public void getAllMovies(GetMovieItemListListener<Movie> listener) {
        this.db.collection(MOVIE_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<Movie> movies = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = Movie.fromJson(document.getData());
                            movies.add(movie);
                        }
                    }
                    listener.onComplete(movies);
                });
    }

    // TODO: I'm not sure that we need to implement this with firebase,
    //  because this function will be called in front of ROOM.
    public void getAllMoviesByCategoryId(Integer categoryId,
                                         GetMovieItemListListener<Movie> listener) {
        this.db.collection(MOVIE_COLLECTION_NAME)
                .whereEqualTo(MOVIE_CATEGORY_ID, categoryId)
                .get()
                .addOnCompleteListener(task -> {
                    List<Movie> movies = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Movie movie = Movie.fromJson(document.getData());
                            movies.add(movie);
                        }
                    }
                    listener.onComplete(movies);
                });
    }

    public void getMovieById(Integer id, GetMovieItemListener<Movie> listener) {

    }

    // TODO: I'm not sure that we need to implement this with firebase,
    //  because this function will be called in front of ROOM.
    public void getMovieByName(String name, GetMovieItemListener<Movie> listener) {
        this.db.collection(MOVIE_COLLECTION_NAME)
                .whereEqualTo(MOVIE_NAME, name)
                .get()
                .addOnCompleteListener(task -> {
                    Movie movie = null;
                    if (task.isSuccessful()) {
                        QuerySnapshot json = task.getResult();
                        List<DocumentSnapshot> jsonDocument = json.getDocuments();
                        movie = Movie.fromJson(jsonDocument.get(0).getData());
                    }
                    listener.onComplete(movie);
                });
    }

    public void addMovie(Movie movie, ExecuteMovieItemListener listener) {
        this.db.collection(MOVIE_COLLECTION_NAME)
                .add(movie.toJson())
                .addOnCompleteListener(task -> listener.onComplete());
    }

    public void removeMovie(Integer index, ExecuteMovieItemListener listener) {

    }

    public void updateMovie(Integer index, Movie movie, ExecuteMovieItemListener listener) {

    }
}
