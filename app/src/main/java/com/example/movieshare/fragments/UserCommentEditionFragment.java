package com.example.movieshare.fragments;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentUserCommentEditionBinding;
import com.example.movieshare.repository.MovieComment;
import com.example.movieshare.repository.Repository;

public class UserCommentEditionFragment extends Fragment {
    private FragmentUserCommentEditionBinding viewBindings;
    private Integer movieCommentPosition;
    private MovieComment movieComment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieCommentPosition =
                UserCommentEditionFragmentArgs.fromBundle(getArguments()).getMovieCommentPosition();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentUserCommentEditionBinding.inflate(inflater, container, false);
        this.movieComment = Repository.instance().getAllMovieComments().get(this.movieCommentPosition);
        displayUserMovieCommentDetails();
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
        return this.viewBindings.getRoot();
    }

    private void displayUserMovieCommentDetails(){
        this.viewBindings.userCommentEditionFragmentMovieNameInputEt.setText(this.movieComment.getMovieName());
        this.viewBindings.userCommentEditionFragmentMovieRatingInputEt.setText(this.movieComment.getMovieRating());
        this.viewBindings.userCommentEditionFragmentMovieCommentInputEt.setText(this.movieComment.getDescription());
        setUserCommentPropertiesState();
    }

    private void setUserCommentPropertiesState(){
        this.viewBindings.userCommentEditionFragmentMovieNameInputEt.setFocusable(false);
        this.viewBindings.userCommentEditionFragmentMovieRatingInputEt.setFocusable(true);
        this.viewBindings.userCommentEditionFragmentMovieCommentInputEt.setFocusable(true);
    }

    private void deleteUserComment() {
        Repository.instance().removeMovieComment(this.movieCommentPosition);
    }

    private void updateUserComment(){
        MovieComment movieComment = Repository.instance().getAllMovieComments().get(this.movieCommentPosition);
        String updatedMovieRating = this.viewBindings.userCommentEditionFragmentMovieRatingInputEt.getText().toString();
        String updatedMovieComment = this.viewBindings.userCommentEditionFragmentMovieCommentInputEt.getText().toString();
        movieComment.setMovieRating(updatedMovieRating);
        movieComment.setDescription(updatedMovieComment);
        Repository.instance().setMovieComment(this.movieCommentPosition, movieComment);
    }
}