package com.example.movieshare.fragments.user;

import android.os.Bundle;

import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentUserCommentAdditionBinding;
import com.example.movieshare.fragments.dialogs.AddUserMovieCommentDialogFragment;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.Repository;

import java.util.Objects;

public class UserCommentAdditionFragment extends UserCommentFormFragment {
    private FragmentUserCommentAdditionBinding viewBindings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentUserCommentAdditionBinding.inflate(inflater, container, false);
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
            MovieComment movieComment = buildNewMovieComment();
            Repository.getMovieCommentHandler()
                    .addMovieComment(movieComment, () -> {
                        new AddUserMovieCommentDialogFragment()
                                .show(getActivity().getSupportFragmentManager(), "TAG");
                        Navigation.findNavController(view).popBackStack();
                    });
        });
    }

    private MovieComment buildNewMovieComment() {
        Integer id = 1;
        Integer userId = 1;
        Integer movieId = 1;
        String description =
                replaceNullValueIfNeeded(this.viewBindings
                        .userCommentAdditionFragmentMovieCommentInputEt.getText().toString());
        String movieName =
                replaceNullValueIfNeeded(this.viewBindings
                        .userCommentAdditionFragmentMovieNameInputEt.getText().toString());
        String movieRating =
                replaceNullValueIfNeeded(this.viewBindings
                        .userCommentAdditionFragmentMovieRatingInputEt.getText().toString());
        return new MovieComment(id, userId, movieId, description, movieName, movieRating);
    }

    private String replaceNullValueIfNeeded(String content) {
        if (Objects.isNull(content)) {
            return "";
        }
        return content;
    }
}