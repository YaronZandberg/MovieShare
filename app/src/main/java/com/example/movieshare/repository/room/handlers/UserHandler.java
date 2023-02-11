package com.example.movieshare.repository.room.handlers;

import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;

import com.example.movieshare.listeners.GetMovieItemListener;
import com.example.movieshare.repository.models.User;
import com.example.movieshare.repository.room.localdb.AppLocalDB;
import com.example.movieshare.repository.room.localdb.AppLocalDbRepository;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserHandler {
    private static final UserHandler userHandlerInstance = new UserHandler();
    private final Executor executor;
    private final Handler mainThreadHandler;
    private final AppLocalDbRepository localDB;

    private UserHandler() {
        this.executor = Executors.newSingleThreadExecutor();
        this.mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        this.localDB = AppLocalDB.getAppDB();
    }

    public static UserHandler instance() {
        return userHandlerInstance;
    }

    public LiveData<List<User>> getAllUsers() {
        return this.localDB.userDao().getAllUsers();
    }

    public void getUserById(String userId, GetMovieItemListener<User> listener) {
        this.executor.execute(() -> {
            User user = localDB.userDao().getUserById(userId);
            mainThreadHandler.post(() -> listener.onComplete(user));
        });
    }

    public void addUser(User user) {
        this.localDB.userDao().insertAll(user);
    }
}
