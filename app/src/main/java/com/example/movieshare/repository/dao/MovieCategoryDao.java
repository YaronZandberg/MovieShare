package com.example.movieshare.repository.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieshare.repository.models.MovieCategory;

import java.util.List;

@Dao
public interface MovieCategoryDao {
    @Query("SELECT * FROM MovieCategory")
    List<MovieCategory> getAllMovieCategories();

    @Query("SELECT * FROM MovieCategory WHERE categoryId = :id")
    MovieCategory getMovieCategoryById(Integer id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(MovieCategory... movieCategories);

    @Delete
    void delete(MovieCategory movieCategory);
}
