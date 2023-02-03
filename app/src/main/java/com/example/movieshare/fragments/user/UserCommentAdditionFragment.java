package com.example.movieshare.fragments.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.R;
import com.example.movieshare.databinding.FragmentUserCommentAdditionBinding;
import com.example.movieshare.fragments.dialogs.AddUserMovieCommentDialogFragment;
import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.Repository;

import java.util.Objects;

public class UserCommentAdditionFragment extends UserCommentFormFragment {
    private FragmentUserCommentAdditionBinding viewBindings;
    private Movie movie;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings =
                FragmentUserCommentAdditionBinding.inflate(inflater, container, false);
        displayUserMovieCommentDetails();
        configureMenuOptions();
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    protected void displayUserMovieCommentDetails() {
        setUserCommentPropertiesState();
    }

    @Override
    protected void setUserCommentPropertiesState() {
        this.viewBindings.userCommentAdditionFragmentMovieNameInputEt.setFocusable(true);
        this.viewBindings.userCommentAdditionFragmentMovieRatingInputEt.setFocusable(true);
        this.viewBindings.userCommentAdditionFragmentMovieCommentInputEt.setFocusable(true);
    }

    @Override
    protected void activateButtonsListeners() {
        this.viewBindings.userCommentAdditionFragmentCancelBtn.setOnClickListener(view ->
                Navigation.findNavController(view).popBackStack());
        this.viewBindings.userCommentAdditionFragmentSaveBtn.setOnClickListener(view -> {
            // TODO: Find a way to update the movie comment only if a movie exist
            validateExistingMovie();
            MovieComment movieComment = buildNewMovieComment();
            Repository.getMovieCommentHandler()
                    .addMovieComment(movieComment, () -> {
                        new AddUserMovieCommentDialogFragment()
                                .show(getActivity().getSupportFragmentManager(), "TAG");
                        Navigation.findNavController(view).popBackStack();
                    });

        });
    }

    private void validateExistingMovie() {
        String movieName = this.viewBindings
                .userCommentAdditionFragmentMovieNameInputEt.getText().toString();
        Repository.getMovieHandler()
                .getMovieByName(movieName, movieItem -> {
                    this.movie = movieItem;
                    if (Objects.isNull(this.movie)) {
                        throw new Exception("There is no movie whose name is: " + movieName);
                    }
                });
    }

    private MovieComment buildNewMovieComment() {
        Integer userId = 1;
        Integer movieId = this.movie.getMovieId();
        String movieName = this.viewBindings
                .userCommentAdditionFragmentMovieNameInputEt.getText().toString();
        String movieRatingOfComment = this.viewBindings
                .userCommentAdditionFragmentMovieRatingInputEt.getText().toString();
        String description = this.viewBindings
                .userCommentAdditionFragmentMovieCommentInputEt.getText().toString();
        return new MovieComment(userId, movieId, movieName, movieRatingOfComment, description);
    }

    private void configureMenuOptions() {
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.userCommentAdditionFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    Navigation.findNavController(viewBindings.getRoot()).popBackStack();
                    return true;
                } else {
                    if (Objects.nonNull(viewBindings)) {
                        NavDirections action = UserCommentAdditionFragmentDirections.actionGlobalUserProfileFragment();
                        Navigation.findNavController(viewBindings.getRoot()).navigate(action);
                        return true;
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}