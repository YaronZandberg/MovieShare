package com.example.movieshare.repository.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieshare.repository.models.MovieCategory;

import java.util.List;

@Dao
public interface MovieCategoryDao {
    @Query("SELECT * FROM MovieCategory")
    LiveData<List<MovieCategory>> getAllMovieCategories();

    @Query("SELECT * FROM MovieCategory WHERE categoryId = :id")
    MovieCategory getMovieCategoryById(String id);

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAll(MovieCategory... movieCategories);
}
