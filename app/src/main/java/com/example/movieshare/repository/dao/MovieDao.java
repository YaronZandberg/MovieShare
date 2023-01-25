package com.example.movieshare.repository.dao;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieshare.repository.models.Movie;

import java.util.List;

public interface MovieDao {
    @Query("SELECT * FROM Movie")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM Movie WHERE movieId = :id")
    Movie getMovieById(Integer id);

    // --- Add another query with FK: getAllMoviesByCategoryId ---
    //public void addMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Movie... movies);

    @Delete
    void delete(Movie movie);
}
