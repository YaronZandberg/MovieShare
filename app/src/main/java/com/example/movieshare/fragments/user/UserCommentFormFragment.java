package com.example.movieshare.fragments.user;

import androidx.fragment.app.Fragment;

public abstract class UserCommentFormFragment extends Fragment {

    protected abstract void displayUserMovieCommentDetails();

    protected abstract void setUserCommentPropertiesState();

    protected abstract void activateButtonsListeners();
}