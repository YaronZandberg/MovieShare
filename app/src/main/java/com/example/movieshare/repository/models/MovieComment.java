package com.example.movieshare.repository.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = {
        @ForeignKey(entity = Movie.class,
                parentColumns = "movieId",
                childColumns = "movieId",
                onDelete = ForeignKey.CASCADE)
})
public class MovieComment {
    @PrimaryKey(autoGenerate = true)
    private Integer serialId;

    @NonNull
    private Integer userId;

    @ColumnInfo(index = true)
    @NonNull
    private Integer movieId;
    private String movieName;
    private String movieRatingOfComment;
    private String description;

    public MovieComment(@NonNull Integer userId, @NonNull Integer movieId,
                        String movieName, String movieRatingOfComment, String description) {
        this.userId = userId;
        this.movieId = movieId;
        this.movieName = movieName;
        this.movieRatingOfComment = movieRatingOfComment;
        this.description = description;
    }

    @NonNull
    public Integer getSerialId() {
        return this.serialId;
    }

    public void setSerialId(@NonNull Integer serialId) {
        this.serialId = serialId;
    }

    @NonNull
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(@NonNull Integer userId) {
        this.userId = userId;
    }

    @NonNull
    public Integer getMovieId() {
        return this.movieId;
    }

    public void setMovieId(@NonNull Integer movieId) {
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
