package com.example.movieshare.viewmodels.user;

import androidx.activity.result.ActivityResultLauncher;
import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.models.User;

public class UserProfileEditionFragmentViewModel extends ViewModel {
    private User user;
    private ActivityResultLauncher<Void> cameraLauncher = null;
    private boolean isProfilePictureSelected = false;

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
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
        this.isProfilePictureSelected = profilePictureSelected;
    }
}
