package com.example.movieshare.fragments.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentUserCommentAdditionBinding;
import com.example.movieshare.fragments.dialogs.AddUserMovieCommentDialogFragment;
import com.example.movieshare.fragments.dialogs.NonExistingMovieDialogFragment;
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
            try {
                validateExistingMovie();
                MovieComment movieComment = buildNewMovieComment();
                Repository.getMovieCommentHandler()
                        .addMovieComment(movieComment, () -> {
                            new AddUserMovieCommentDialogFragment()
                                    .show(getActivity().getSupportFragmentManager(), "TAG");
                            Navigation.findNavController(view).popBackStack();
                        });
            } catch (Exception e) {
                new NonExistingMovieDialogFragment()
                        .show(getActivity().getSupportFragmentManager(), "TAG");
            }
        });
    }

    // TODO: Put the throw section code after the getMovieByName method because it is async.
    //  In addition, create a specific listener for this method that will have throws in her signature.
    private void validateExistingMovie() throws Exception {
        String movieName = this.viewBindings
                .userCommentAdditionFragmentMovieNameInputEt.getText().toString();
        Repository.getMovieHandler()
                .getMovieByName(movieName, movieItem -> this.movie = movieItem);
        if (Objects.isNull(this.movie)) {
            throw new Exception("There is no movie whose name is: " + movieName);
        }
    }

    private MovieComment buildNewMovieComment() {
        Integer userId = 1;
        Integer movieId = 1;
        String description = this.viewBindings
                .userCommentAdditionFragmentMovieCommentInputEt.getText().toString();
        String movieName = this.viewBindings
                .userCommentAdditionFragmentMovieNameInputEt.getText().toString();
        String movieRating = this.viewBindings
                .userCommentAdditionFragmentMovieRatingInputEt.getText().toString();
        return new MovieComment(userId, movieId, description, movieName, movieRating);
    }
}