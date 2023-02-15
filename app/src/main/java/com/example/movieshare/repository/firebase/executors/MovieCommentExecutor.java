package com.example.movieshare.repository.firebase.executors;

import static com.example.movieshare.constants.MovieCommentConstants.*;

import com.example.movieshare.listeners.movies.*;
import com.example.movieshare.repository.models.MovieComment;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MovieCommentExecutor {
    private static final MovieCommentExecutor movieCommentExecutorInstance = new MovieCommentExecutor();
    private final FirebaseFirestore db;

    private MovieCommentExecutor() {
        this.db = FirebaseFirestore.getInstance();
    }

    public static MovieCommentExecutor instance() {
        return movieCommentExecutorInstance;
    }

    public void getAllMovieComments(GetMovieItemListListener<MovieComment> listener) {
        this.db.collection(MOVIE_COMMENT_COLLECTION_NAME)
                .get()
                .addOnCompleteListener(task -> {
                    List<MovieComment> movieComments = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MovieComment movieComment = MovieComment.fromJson(document.getData());
                            movieComment.setMovieCommentId(document.getId());
                            movieComments.add(movieComment);
                        }
                    }
                    listener.onComplete(movieComments);
                });
    }

    public void getAllMovieCommentsSinceLastUpdate(Long localLastUpdate,
                                                   GetMovieItemListListener<MovieComment> listener) {
        this.db.collection(MOVIE_COMMENT_COLLECTION_NAME)
                .whereGreaterThanOrEqualTo(MOVIE_COMMENT_LAST_UPDATE,
                        new Timestamp(localLastUpdate, 0))
                .get()
                .addOnCompleteListener(task -> {
                    List<MovieComment> movieComments = new ArrayList<>();
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            MovieComment movieComment = MovieComment.fromJson(document.getData());
                            movieComment.setMovieCommentId(document.getId());
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

    // TODO: Test implementation
    public void removeMovieComment(String movieCommentId, ExecuteMovieItemListener listener) {
        db.collection(MOVIE_COMMENT_COLLECTION_NAME)
                .document(movieCommentId)
                .delete()
                .addOnCompleteListener(task -> listener.onComplete());
    }

    // TODO: Test implementation
    public void updateMovieComment(String movieCommentId, MovieComment movieComment,
                                   ExecuteMovieItemListener listener) {
        db.collection(MOVIE_COMMENT_COLLECTION_NAME)
                .document(movieCommentId)
                .set(movieComment.toJson())
                .addOnCompleteListener(task -> listener.onComplete());
    }
}
