package com.example.movieshare.viewmodels.user;

import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.models.User;

public class UserProfileEditionFragmentViewModel extends ViewModel {
    private User user;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
