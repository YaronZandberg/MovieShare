package com.example.movieshare.repository.localdb;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.movieshare.repository.dao.*;
import com.example.movieshare.repository.models.*;

@Database(entities = {MovieComment.class, Movie.class, MovieCategory.class}, version = 1)
public abstract class AppLocalDbRepository extends RoomDatabase {
    public abstract MovieCommentDao movieCommentDao();
    public abstract MovieDao movieDao();
    public abstract MovieCategoryDao movieCategoryDao();
}
