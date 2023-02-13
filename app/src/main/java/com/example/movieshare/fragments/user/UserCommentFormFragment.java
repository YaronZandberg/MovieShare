package com.example.movieshare.fragments.user;

import com.example.movieshare.fragments.base.MovieBaseFragment;

public abstract class UserCommentFormFragment extends MovieBaseFragment {

    protected abstract void displayUserMovieCommentDetails();

    protected abstract void setUserCommentPropertiesState();

    protected abstract void activateButtonsListeners();
}