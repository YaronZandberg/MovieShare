package com.example.movieshare.repository.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.movieshare.repository.models.MovieComment;

import java.util.List;

@Dao
public interface MovieCommentDao {
    @Query("SELECT * FROM MovieComment")
    LiveData<List<MovieComment>> getAllMovieComments();

    @Query("SELECT * FROM MovieComment")
    List<MovieComment> getAllMovieCommentsCurrent();

    @Query("SELECT * FROM MovieComment WHERE movieId = :movieId")
    List<MovieComment> getAllMovieCommentsByMovieId(String movieId);

    @Query("SELECT * FROM MovieComment WHERE userId = :userId")
    List<MovieComment> getAllMovieCommentsByUserId(String userId);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(MovieComment... movieComments);

    @Update(onConflict = OnConflictStrategy.IGNORE)
    void updateAll(MovieComment... movieComments);
}
