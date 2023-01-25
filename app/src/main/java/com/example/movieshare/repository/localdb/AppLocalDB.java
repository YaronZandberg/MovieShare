package com.example.movieshare.repository.localdb;

import androidx.room.Room;

import com.example.movieshare.context.MyApplication;

public class AppLocalDB {
    static public AppLocalDbRepository getAppDB() {
        return Room.databaseBuilder(MyApplication.getAppContext(),
                        AppLocalDbRepository.class,
                        "dbFileName.db")
                .fallbackToDestructiveMigration()
                .build();
    }
    private AppLocalDB(){}
}
