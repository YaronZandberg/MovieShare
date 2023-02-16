package com.example.movieshare.repository.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieshare.repository.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM Movie")
    LiveData<List<Movie>> getAllMovies();

    @Query("SELECT * FROM Movie WHERE movieCategoryId = :categoryId")
    List<Movie> getAllMoviesByCategoryId(String categoryId);

    @Query("SELECT * FROM Movie WHERE movieName = :name")
    Movie getMovieByName(String name);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(Movie... movies);
}
