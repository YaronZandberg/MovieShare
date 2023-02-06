package com.example.movieshare.repository.firebase.executors;

import static com.example.movieshare.constants.MovieCommentConstants.*;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.movieshare.listeners.ExecuteMovieItemListener;
import com.example.movieshare.listeners.GetMovieItemListListener;
import com.example.movieshare.listeners.GetMovieItemListener;
import com.example.movieshare.repository.models.MovieComment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    // TODO: I'm not sure that we need to implement this with firebase,
    //  because this function will be called in front of ROOM.
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

    // TODO: I'm not sure that we need to implement this with firebase,
    //  because this function will be called in front of ROOM.
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

    // TODO: Test implementation
    public void removeMovieComment(String movieCommentId, ExecuteMovieItemListener listener) {
        db.collection(MOVIE_COMMENT_COLLECTION_NAME)
                .document(movieCommentId)
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully deleted!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error deleting document", e);
                    }
                });
    }

    // TODO: Test implementation
    public void updateMovieComment(String movieCommentId, MovieComment movieComment,
                                   ExecuteMovieItemListener listener) {
        db.collection(MOVIE_COMMENT_COLLECTION_NAME)
                .document(movieCommentId)
                .set(movieComment)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d("TAG", "DocumentSnapshot successfully written!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "Error writing document", e);
                    }
                });
    }
}
