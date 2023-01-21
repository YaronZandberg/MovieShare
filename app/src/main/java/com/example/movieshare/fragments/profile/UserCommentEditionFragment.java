package com.example.movieshare.fragments.profile;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentUserCommentEditionBinding;
import com.example.movieshare.repository.MovieComment;
import com.example.movieshare.repository.Repository;

import java.util.Objects;

public class UserCommentEditionFragment extends UserCommentFormFragment {
    private FragmentUserCommentEditionBinding viewBindings;
    private Integer movieCommentPosition;
    private MovieComment movieComment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieCommentPosition =
                UserCommentEditionFragmentArgs.fromBundle(getArguments()).getMovieCommentPosition();
        this.movieComment = Repository.instance().getAllMovieComments().get(this.movieCommentPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentUserCommentEditionBinding.inflate(inflater, container, false);
        displayUserMovieCommentDetails();
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    protected void displayUserMovieCommentDetails() {
        this.viewBindings.userCommentEditionFragmentMovieNameInputEt.setText(this.movieComment.getMovieName());
        this.viewBindings.userCommentEditionFragmentMovieRatingInputEt.setText(this.movieComment.getMovieRating());
        this.viewBindings.userCommentEditionFragmentMovieCommentInputEt.setText(this.movieComment.getDescription());
        setUserCommentPropertiesState();
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
        this.viewBindings.userCommentEditionFragmentDeleteBtn.setOnClickListener(view -> {
            deleteUserComment();
            Navigation.findNavController(view).popBackStack();
        });
        this.viewBindings.userCommentEditionFragmentSaveBtn.setOnClickListener(view -> {
            updateUserComment();
            Navigation.findNavController(view).popBackStack();
        });
    }

    private void deleteUserComment() {
        Repository.instance().removeMovieComment(this.movieCommentPosition);
    }

    private void updateUserComment(){
        String updatedMovieRating =
                replaceNullValueIfNeeded(this.viewBindings
                        .userCommentEditionFragmentMovieRatingInputEt.getText().toString());
        String updatedMovieComment =
                replaceNullValueIfNeeded(this.viewBindings
                        .userCommentEditionFragmentMovieCommentInputEt.getText().toString());
        this.movieComment.setMovieRating(updatedMovieRating);
        this.movieComment.setDescription(updatedMovieComment);
        Repository.instance().setMovieComment(this.movieCommentPosition, this.movieComment);
    }

    private String replaceNullValueIfNeeded(String content){
        if (Objects.isNull(content)){
            return "";
        }
        return content;
    }
}