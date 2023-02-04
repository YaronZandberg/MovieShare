package com.example.movieshare.fragments.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.viewmodels.user.UserCommentAdditionFragmentViewModel;

import java.util.Objects;

public class UserCommentAdditionFragment extends UserCommentFormFragment {
    private FragmentUserCommentAdditionBinding viewBindings;
    private String movieName;
    private UserCommentAdditionFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieName = UserCommentAdditionFragmentArgs.fromBundle(getArguments()).getMovieName();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentUserCommentAdditionBinding.inflate(inflater, container, false);
        initializeMovie();
        configureMenuOptions();
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(UserCommentAdditionFragmentViewModel.class);
    }

    public void initializeMovie() {
        Repository.getRepositoryInstance().getLocalModel().getMovieHandler()
                .getMovieByName(this.movieName, movie -> {
            this.viewModel.setMovie(movie);
            displayUserMovieCommentDetails();
        });
    }

    @Override
    protected void displayUserMovieCommentDetails() {
        this.viewBindings.userCommentAdditionFragmentMovieNameInputEt.setText(this.movieName);
        setUserCommentPropertiesState();
    }

    @Override
    protected void setUserCommentPropertiesState() {
        this.viewBindings.userCommentAdditionFragmentMovieNameInputEt.setFocusable(false);
        this.viewBindings.userCommentAdditionFragmentMovieRatingInputEt.setFocusable(true);
        this.viewBindings.userCommentAdditionFragmentMovieCommentInputEt.setFocusable(true);
    }

    @Override
    protected void activateButtonsListeners() {
        this.viewBindings.userCommentAdditionFragmentCancelBtn.setOnClickListener(view ->
                Navigation.findNavController(view).popBackStack());
        this.viewBindings.userCommentAdditionFragmentSaveBtn.setOnClickListener(view -> {
            MovieComment movieComment = buildNewMovieComment();
            Repository.getRepositoryInstance().getLocalModel().getMovieCommentHandler()
                    .addMovieComment(movieComment, () -> {
                        new AddUserMovieCommentDialogFragment()
                                .show(getActivity().getSupportFragmentManager(), "TAG");
                        Navigation.findNavController(view).popBackStack();
                    });

        });
    }

    private MovieComment buildNewMovieComment() {
        Integer userId = 1;
        Integer movieId = this.viewModel.getMovie().getMovieId();
        String movieName = this.movieName;
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