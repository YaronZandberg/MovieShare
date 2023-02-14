package com.example.movieshare.repository.room.localdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.movieshare.repository.models.*;
import com.example.movieshare.repository.room.dao.*;

@Database(entities = {MovieComment.class, Movie.class, MovieCategory.class, User.class}, version = 1)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract MovieCommentDao movieCommentDao();
    public abstract MovieDao movieDao();
    public abstract MovieCategoryDao movieCategoryDao();
    public abstract UserDao userDao();
}
