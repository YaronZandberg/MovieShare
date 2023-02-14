package com.example.movieshare.viewmodels.authentication;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.ViewModel;
import androidx.navigation.NavController;

public class SignUpFragmentViewModel extends ViewModel {
    private NavController navController;
    private ActivityResultLauncher<Void> cameraLauncher = null;
    private boolean isProfilePictureSelected = false;

    public NavController getNavController() {
        return this.navController;
    }

    public void setNavController(NavController navController) {
        this.navController = navController;
    }

    public ActivityResultLauncher<Void> getCameraLauncher() {
        return this.cameraLauncher;
    }

    public void setCameraLauncher(ActivityResultLauncher<Void> cameraLauncher) {
        this.cameraLauncher = cameraLauncher;
    }

    public boolean isProfilePictureSelected() {
        return this.isProfilePictureSelected;
    }

    public void setProfilePictureSelected(boolean profilePictureSelected) {
        isProfilePictureSelected = profilePictureSelected;
    }
}
