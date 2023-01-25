package com.example.movieshare.repository.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieshare.repository.models.MovieComment;

import java.util.List;

@Dao
public interface MovieCommentDao {
    @Query("SELECT * FROM MovieComment")
    List<MovieComment> getAllMovieComments();

    @Query("SELECT * FROM MovieComment WHERE serialId = :id")
    MovieComment getMovieCommentById(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieComment... movieComments);

    @Delete
    void delete(MovieComment movieComment);
}
