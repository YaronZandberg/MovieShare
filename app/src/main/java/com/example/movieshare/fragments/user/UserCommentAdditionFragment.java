package com.example.movieshare.fragments.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentUserCommentAdditionBinding;
import com.example.movieshare.fragments.dialogs.AddUserMovieCommentDialogFragment;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.viewmodels.user.UserCommentAdditionFragmentViewModel;

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
        this.configureMenuOptions(this.viewBindings.getRoot());
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
            Repository.getRepositoryInstance().getFirebaseModel().getMovieCommentExecutor()
                    .addMovieComment(movieComment, () -> {
                        new AddUserMovieCommentDialogFragment()
                                .show(getActivity().getSupportFragmentManager(), "TAG");
                        Repository.getRepositoryInstance().refreshAllMovieComments();
                        Navigation.findNavController(view).popBackStack();
                    });
        });
    }

    // TODO: Put the userId of the user instead of dummy 1
    private MovieComment buildNewMovieComment() {
        Integer userId = 1;
        String movieId = this.viewModel.getMovie().getMovieId();
        String movieName = this.movieName;
        String movieRatingOfComment = this.viewBindings
                .userCommentAdditionFragmentMovieRatingInputEt.getText().toString();
        String description = this.viewBindings
                .userCommentAdditionFragmentMovieCommentInputEt.getText().toString();
        return new MovieComment(userId, movieId, movieName, movieRatingOfComment, description);
    }
}