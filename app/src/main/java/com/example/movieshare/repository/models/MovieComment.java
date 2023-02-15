package com.example.movieshare.repository.models;

import static com.example.movieshare.constants.MovieCommentConstants.*;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.movieshare.context.MyApplication;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FieldValue;

import java.util.HashMap;
import java.util.Map;

@Entity(foreignKeys = {
        @ForeignKey(entity = Movie.class,
                parentColumns = "movieId",
                childColumns = "movieId",
                onDelete = ForeignKey.CASCADE)
})
public class MovieComment {
    @PrimaryKey
    @NonNull
    private String movieCommentId;

    @NonNull
    private String userId;

    @ColumnInfo(index = true)
    @NonNull
    private String movieId;

    private String movieName;
    private String movieRatingOfComment;
    private String description;
    private Long movieCommentLastUpdate;

    public MovieComment(@NonNull String userId, @NonNull String movieId,
                        String movieName, String movieRatingOfComment, String description) {
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieRatingOfComment = movieRatingOfComment;
        this.description = description;
    }

    @Ignore
    public MovieComment(@NonNull String movieCommentId, @NonNull String userId, @NonNull String movieId,
                        String movieName, String movieRatingOfComment, String description) {
        this.movieCommentId = movieCommentId;
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieRatingOfComment = movieRatingOfComment;
        this.description = description;
    }

    public static MovieComment fromJson(Map<String, Object> json) {
        String movieCommentId = String.valueOf(json.get(MOVIE_COMMENT_ID));
        String userId = String.valueOf(json.get(MOVIE_COMMENT_USER_ID));
        String movieId = String.valueOf(json.get(MOVIE_COMMENT_MOVIE_ID));
        String movieName = String.valueOf(json.get(MOVIE_COMMENT_MOVIE_NAME));
        String movieRatingOfComment = String.valueOf(json.get(MOVIE_COMMENT_RATING));
        String description = String.valueOf(json.get(MOVIE_COMMENT_DESCRIPTION));
        MovieComment movieComment = new MovieComment(movieCommentId, userId, movieId, movieName, movieRatingOfComment, description);
        Timestamp lastUpdate = (Timestamp) json.get(MOVIE_COMMENT_LAST_UPDATE);
        movieComment.setMovieCommentLastUpdate(lastUpdate.getSeconds());
        return movieComment;
    }

    public Map<String, Object> toJson() {
        Map<String, Object> movieCommentJson = new HashMap<>();
        movieCommentJson.put(MOVIE_COMMENT_ID, this.getMovieCommentId());
        movieCommentJson.put(MOVIE_COMMENT_USER_ID, this.getUserId());
        movieCommentJson.put(MOVIE_COMMENT_MOVIE_ID, this.getMovieId());
        movieCommentJson.put(MOVIE_COMMENT_MOVIE_NAME, this.getMovieName());
        movieCommentJson.put(MOVIE_COMMENT_RATING, this.getMovieRatingOfComment());
        movieCommentJson.put(MOVIE_COMMENT_DESCRIPTION, this.getDescription());
        movieCommentJson.put(MOVIE_COMMENT_LAST_UPDATE, FieldValue.serverTimestamp());
        return movieCommentJson;
    }

    public static Long getLocalLastUpdate() {
        return MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .getLong(MOVIE_COMMENT_LOCAL_LAST_UPDATE, 0);
    }

    public static void setLocalLastUpdate(Long localLastUpdate) {
        MyApplication.getAppContext().getSharedPreferences("TAG", Context.MODE_PRIVATE)
                .edit().putLong(MOVIE_COMMENT_LOCAL_LAST_UPDATE, localLastUpdate).commit();
    }

    @NonNull
    public String getMovieCommentId() {
        return this.movieCommentId;
    }

    public void setMovieCommentId(@NonNull String movieCommentId) {
        this.movieCommentId = movieCommentId;
    }

    @NonNull
    public String getUserId() {
        return this.userId;
    }

    public void setUserId(@NonNull String userId) {
        this.userId = userId;
    }

    @NonNull
    public String getMovieId() {
        return this.movieId;
    }

    public void setMovieId(@NonNull String movieId) {
        this.movieId = movieId;
    }

    public String getMovieName() {
        return this.movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getMovieRatingOfComment() {
        return this.movieRatingOfComment;
    }

    public void setMovieRatingOfComment(String movieRatingOfComment) {
        this.movieRatingOfComment = movieRatingOfComment;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getMovieCommentLastUpdate() {
        return this.movieCommentLastUpdate;
    }

    public void setMovieCommentLastUpdate(Long movieCommentLastUpdate) {
        this.movieCommentLastUpdate = movieCommentLastUpdate;
    }
}
