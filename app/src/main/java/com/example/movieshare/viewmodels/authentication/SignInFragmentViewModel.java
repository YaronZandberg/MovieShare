package com.example.movieshare.viewmodels.authentication;

import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

public class SignInFragmentViewModel extends ViewModel {
    private NavController navController;

    public NavController getNavController() {
        return this.navController;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }
}
