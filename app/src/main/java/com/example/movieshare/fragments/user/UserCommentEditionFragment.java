package com.example.movieshare.fragments.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentUserCommentEditionBinding;
import com.example.movieshare.fragments.dialogs.DeleteUserMovieCommentDialogFragment;
import com.example.movieshare.fragments.dialogs.UpdateUserMovieCommentDialogFragment;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.Repository;

import java.util.List;
import java.util.Objects;

public class UserCommentEditionFragment extends UserCommentFormFragment {
    private FragmentUserCommentEditionBinding viewBindings;
    private Integer movieCommentPosition;
    private Integer userId;
    private List<MovieComment> allUserMovieComments;
    private MovieComment movieComment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieCommentPosition =
                UserCommentEditionFragmentArgs.fromBundle(getArguments()).getMovieCommentPosition();
        this.userId = UserCommentEditionFragmentArgs.fromBundle(getArguments()).getUserId();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentUserCommentEditionBinding.inflate(inflater, container, false);
        reloadUserMovieComments();
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    private void reloadUserMovieComments() {
        Repository.getMovieCommentHandler()
                .getAllMovieCommentsByUserId(this.userId, movieCommentList -> {
                    this.allUserMovieComments = movieCommentList;
                    this.movieComment = this.allUserMovieComments.get(this.movieCommentPosition);
                    displayUserMovieCommentDetails();
                });
    }

    @Override
    protected void displayUserMovieCommentDetails() {
        if (Objects.nonNull(this.movieComment)) {
            this.viewBindings.userCommentEditionFragmentMovieNameInputEt.setText(this.movieComment.getMovieName());
            this.viewBindings.userCommentEditionFragmentMovieRatingInputEt.setText(this.movieComment.getMovieRating());
            this.viewBindings.userCommentEditionFragmentMovieCommentInputEt.setText(this.movieComment.getDescription());
            setUserCommentPropertiesState();
        }
    }

    @Override
    protected void setUserCommentPropertiesState() {
        this.viewBindings.userCommentEditionFragmentMovieNameInputEt.setFocusable(false);
        this.viewBindings.userCommentEditionFragmentMovieRatingInputEt.setFocusable(true);
        this.viewBindings.userCommentEditionFragmentMovieCommentInputEt.setFocusable(true);
    }

    @Override
    protected void activateButtonsListeners() {
        this.viewBindings.userCommentEditionFragmentCancelBtn.setOnClickListener(view ->
                Navigation.findNavController(view).popBackStack());
        this.viewBindings.userCommentEditionFragmentDeleteBtn.setOnClickListener(view ->
                Repository.getMovieCommentHandler()
                        .removeMovieComment(this.movieCommentPosition, () -> {
                            new DeleteUserMovieCommentDialogFragment()
                                    .show(getActivity().getSupportFragmentManager(), "TAG");
                            Navigation.findNavController(view).popBackStack();
                        }));
        this.viewBindings.userCommentEditionFragmentSaveBtn.setOnClickListener(view -> {
            updateUserComment();
            new UpdateUserMovieCommentDialogFragment()
                    .show(getActivity().getSupportFragmentManager(), "TAG");
            Navigation.findNavController(view).popBackStack();
        });
    }

    // TODO: Use the updateUserComment method of DB version and put all this code in the
    //  setOnClickListener method of save button
    private void updateUserComment() {
        String updatedMovieRating = this.viewBindings
                .userCommentEditionFragmentMovieRatingInputEt.getText().toString();
        String updatedMovieComment = this.viewBindings
                .userCommentEditionFragmentMovieCommentInputEt.getText().toString();
        this.movieComment.setMovieRating(updatedMovieRating);
        this.movieComment.setDescription(updatedMovieComment);
        Repository.getMovieCommentHandler().setMovieComment(this.movieCommentPosition, this.movieComment);
    }
}