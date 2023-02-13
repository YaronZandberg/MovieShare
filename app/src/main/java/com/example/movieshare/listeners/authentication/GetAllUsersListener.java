package com.example.movieshare.listeners.authentication;

import com.example.movieshare.repository.models.User;

import java.util.List;

public interface GetAllUsersListener {
    void onComplete(List<User> list);
}
