package com.example.movieshare.repository.models;

import static com.example.movieshare.constants.MovieCommentConstants.*;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.HashMap;
import java.util.Map;

// TODO: Add another foreign key of userId
@Entity(foreignKeys = {
        @ForeignKey(entity = Movie.class,
                parentColumns = "movieId",
                childColumns = "movieId",
                onDelete = ForeignKey.CASCADE)
})
public class MovieComment {
    @PrimaryKey @NonNull
    private String movieCommentId;

    @NonNull
    private Integer userId;

    @ColumnInfo(index = true)
    @NonNull
    private String movieId;
    private String movieName;
    private String movieRatingOfComment;
    private String description;

    public MovieComment(@NonNull Integer userId, @NonNull String movieId,
                        String movieName, String movieRatingOfComment, String description) {
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieRatingOfComment = movieRatingOfComment;
        this.description = description;
    }

    @Ignore
    public MovieComment(@NonNull String movieCommentId, @NonNull Integer userId, @NonNull String movieId,
                        String movieName, String movieRatingOfComment, String description) {
        this.movieCommentId = movieCommentId;
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieRatingOfComment = movieRatingOfComment;
        this.description = description;
    }

    // TODO: handle exceptions from casting or null
    public static MovieComment fromJson(Map<String, Object> json) {
        String movieCommentId = String.valueOf(json.get(MOVIE_COMMENT_ID));
        Integer userId = Integer.parseInt(String.valueOf(json.get(MOVIE_COMMENT_USER_ID)));
        String movieId = String.valueOf(json.get(MOVIE_COMMENT_MOVIE_ID));
        String movieName = String.valueOf(json.get(MOVIE_COMMENT_MOVIE_NAME));
        String movieRatingOfComment = String.valueOf(json.get(MOVIE_COMMENT_RATING));
        String description = String.valueOf(json.get(MOVIE_COMMENT_DESCRIPTION));
        return new MovieComment(movieCommentId, userId, movieId, movieName, movieRatingOfComment, description);
    }

    public Map<String, Object> toJson() {
        Map<String, Object> movieCommentJson = new HashMap<>();
        movieCommentJson.put(MOVIE_COMMENT_ID, this.getMovieCommentId());
        movieCommentJson.put(MOVIE_COMMENT_USER_ID, this.getUserId());
        movieCommentJson.put(MOVIE_COMMENT_MOVIE_ID, this.getMovieId());
        movieCommentJson.put(MOVIE_COMMENT_MOVIE_NAME, this.getMovieName());
        movieCommentJson.put(MOVIE_COMMENT_RATING, this.getMovieRatingOfComment());
        movieCommentJson.put(MOVIE_COMMENT_DESCRIPTION, this.getDescription());
        return movieCommentJson;
    }

    @NonNull
    public String getMovieCommentId() {
        return this.movieCommentId;
    }

    public void setMovieCommentId(@NonNull String movieCommentId) {
        this.movieCommentId = movieCommentId;
    }

    @NonNull
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(@NonNull Integer userId) {
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
}
