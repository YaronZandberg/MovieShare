package com.example.movieshare.repository.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.movieshare.repository.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Query("SELECT * FROM User WHERE userId = :id")
    User getUserById(String id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);
}
