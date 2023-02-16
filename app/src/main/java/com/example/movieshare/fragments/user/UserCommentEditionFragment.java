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

import com.example.movieshare.databinding.FragmentUserCommentEditionBinding;
import com.example.movieshare.fragments.dialogs.UpdateUserMovieCommentDialogFragment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.UserUtils;
import com.example.movieshare.viewmodels.user.UserCommentEditionFragmentViewModel;

import java.util.Objects;

public class UserCommentEditionFragment extends UserCommentFormFragment {
    private FragmentUserCommentEditionBinding viewBindings;
    private Integer userMovieCommentPosition;
    private String userId;
    private UserCommentEditionFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userMovieCommentPosition =
                UserCommentEditionFragmentArgs.fromBundle(getArguments()).getMovieCommentPosition();
        this.userId = UserCommentEditionFragmentArgs.fromBundle(getArguments()).getUserId();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeAllMovieComment();
        this.viewBindings = FragmentUserCommentEditionBinding.inflate(inflater, container, false);
        reloadUserMovieComments();
        this.configureMenuOptions(this.viewBindings.getRoot());
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(UserCommentEditionFragmentViewModel.class);
    }

    private void initializeAllMovieComment() {
        Repository.getRepositoryInstance().refreshAllMovieComments();
    }

    private void reloadUserMovieComments() {
        if (Objects.nonNull(this.viewModel.getAllMovieComments().getValue())) {
            Repository.getRepositoryInstance().getLocalModel().getMovieCommentHandler()
                    .getAllMovieCommentsByUserId(this.userId, movieCommentList -> {
                        this.viewModel.setUserMovieComments(movieCommentList);
                        this.viewModel.setMovieComment(this.viewModel.getUserMovieComments()
                                .get(this.userMovieCommentPosition));
                        findMovieCommentPositionInTotalList();
                    });
        }
    }

    private void findMovieCommentPositionInTotalList() {
        if (Objects.nonNull(this.viewModel.getAllMovieComments().getValue())) {
            Integer movieCommentIndex = this.viewModel.getAllMovieComments().getValue()
                    .indexOf(this.viewModel.getMovieComment());
            this.viewModel.setMovieCommentPositionInTotalList(movieCommentIndex);
            displayUserMovieCommentDetails();
        }
    }

    @Override
    protected void displayUserMovieCommentDetails() {
        if (Objects.nonNull(this.viewModel.getMovieComment())) {
            this.viewBindings.userCommentEditionFragmentMovieNameInputEt.setText(this.viewModel.getMovieComment().getMovieName());
            this.viewBindings.userCommentEditionFragmentMovieRatingInputEt.setText(this.viewModel.getMovieComment().getMovieRatingOfComment());
            this.viewBindings.userCommentEditionFragmentMovieCommentInputEt.setText(this.viewModel.getMovieComment().getDescription());
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
        this.viewBindings.userCommentEditionFragmentSaveBtn.setOnClickListener(view -> {
            if (isFormValid()) {
                saveComment(view);
            }
        });
    }

    private Boolean isFormValid() {
        return (UserUtils.setErrorIfBiggerThan(this.viewBindings.userCommentEditionFragmentMovieRatingInputEt, 5) &&
                UserUtils.setErrorIfEmpty(this.viewBindings.userCommentEditionFragmentMovieCommentInputEt));
    }

    private void saveComment(View view) {
        updateUserComment();
        Repository.getRepositoryInstance().getFirebaseModel().getMovieCommentExecutor()
                .updateMovieComment(this.viewModel.getMovieComment().getMovieCommentId(),
                        this.viewModel.getMovieComment(), () -> {
                            new UpdateUserMovieCommentDialogFragment()
                                    .show(getActivity().getSupportFragmentManager(), "TAG");
                            Repository.getRepositoryInstance().refreshAllMovieComments();
                            Navigation.findNavController(view).popBackStack();
                        });
    }

    private void updateUserComment() {
        String updatedMovieRating = this.viewBindings
                .userCommentEditionFragmentMovieRatingInputEt.getText().toString();
        String updatedMovieDescription = this.viewBindings
                .userCommentEditionFragmentMovieCommentInputEt.getText().toString();
        this.viewModel.getMovieComment().setMovieRatingOfComment(updatedMovieRating);
        this.viewModel.getMovieComment().setDescription(updatedMovieDescription);
    }
}