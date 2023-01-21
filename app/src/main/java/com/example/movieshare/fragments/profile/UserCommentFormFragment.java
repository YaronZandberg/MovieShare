package com.example.movieshare.fragments.profile;

import androidx.fragment.app.Fragment;

public abstract class UserCommentFormFragment extends Fragment {

    protected abstract void displayUserMovieCommentDetails();

    protected abstract void setUserCommentPropertiesState();

    protected abstract void activateButtonsListeners();
}