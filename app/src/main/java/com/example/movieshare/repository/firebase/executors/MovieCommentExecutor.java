package com.example.movieshare.repository.firebase.executors;

import static com.example.movieshare.constants.MovieCommentConstants.*;

import com.example.movieshare.listeners.ExecuteMovieItemListener;
import com.example.movieshare.listeners.GetMovieItemListListener;
import com.example.movieshare.listeners.GetMovieItemListener;
import com.example.movieshare.repository.models.MovieComment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieCommentExecutor {
    private final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MovieCommentExecutor() {
    }

    public void getAllMovieComments(GetMovieItemListListener<MovieComment> listener) {
        this.db.collection(MOVIE_COMMENT_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<MovieComment> movieComments = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MovieComment movieComment = MovieComment.fromJson(document.getData());
                            movieComments.add(movieComment);
                        }
                    }
                    listener.onComplete(movieComments);
                });
    }

    public void getMovieCommentById(Integer id, GetMovieItemListener<MovieComment> listener) {

    }

    public void getAllMovieCommentsByUserId(Integer userId,
                                            GetMovieItemListListener<MovieComment> listener) {
        this.db.collection(MOVIE_COMMENT_COLLECTION_NAME)
                .whereEqualTo(MOVIE_COMMENT_USER_ID, userId)
                .get()
                .addOnCompleteListener(task -> {
                    List<MovieComment> movieComments = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MovieComment movieComment = MovieComment.fromJson(document.getData());
                            movieComments.add(movieComment);
                        }
                    }
                    listener.onComplete(movieComments);
                });
    }

    public void getAllMovieCommentsByMovieId(Integer movieId,
                                             GetMovieItemListListener<MovieComment> listener) {
        this.db.collection(MOVIE_COMMENT_COLLECTION_NAME)
                .whereEqualTo(MOVIE_COMMENT_MOVIE_ID, movieId)
                .get()
                .addOnCompleteListener(task -> {
                    List<MovieComment> movieComments = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MovieComment movieComment = MovieComment.fromJson(document.getData());
                            movieComments.add(movieComment);
                        }
                    }
                    listener.onComplete(movieComments);
                });
    }

    public void addMovieComment(MovieComment movieComment, ExecuteMovieItemListener listener) {
        this.db.collection(MOVIE_COMMENT_COLLECTION_NAME)
                .add(movieComment.toJson())
                .addOnCompleteListener(task -> listener.onComplete());
    }

    // TODO: Add implementation
    public void removeMovieComment(Integer index, ExecuteMovieItemListener listener) {

    }

    // TODO: Add implementation
    public void updateMovieComment(Integer index, MovieComment movieComment,
                                   ExecuteMovieItemListener listener) {

    }
}
