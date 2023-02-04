package com.example.movieshare.repository.room.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieshare.repository.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM Movie")
    List<Movie> getAllMovies();

    @Query("SELECT * FROM Movie WHERE movieCategoryId = :categoryId")
    List<Movie> getAllMoviesByCategoryId(Integer categoryId);

    @Query("SELECT * FROM Movie WHERE movieId = :id")
    Movie getMovieById(Integer id);

    @Query("SELECT * FROM Movie WHERE movieName = :name")
    Movie getMovieByName(String name);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Movie... movies);

    @Delete
    void delete(Movie movie);
}
