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
import com.example.movieshare.databinding.FragmentUserCommentEditionBinding;
import com.example.movieshare.fragments.dialogs.DeleteUserMovieCommentDialogFragment;
import com.example.movieshare.fragments.dialogs.UpdateUserMovieCommentDialogFragment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.viewmodels.user.UserCommentEditionFragmentViewModel;

import java.util.Objects;

public class UserCommentEditionFragment extends UserCommentFormFragment {
    private FragmentUserCommentEditionBinding viewBindings;
    private Integer userMovieCommentPosition;
    private Integer userId;
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
        this.viewBindings = FragmentUserCommentEditionBinding.inflate(inflater, container, false);
        reloadUserMovieComments();
        configureMenuOptions();
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(UserCommentEditionFragmentViewModel.class);
    }

    private void reloadUserMovieComments() {
        Repository.getMovieCommentHandler()
                .getAllMovieCommentsByUserId(this.userId, movieCommentList -> {
                    this.viewModel.setAllUserMovieComments(movieCommentList);
                    this.viewModel.setMovieComment(this.viewModel.getAllUserMovieComments()
                            .get(this.userMovieCommentPosition));
                    findMovieCommentPositionInTotalList();
                });
    }

    private void findMovieCommentPositionInTotalList() {
        Repository.getMovieCommentHandler()
                .getAllMovieComments(allMovieComments -> {
                    this.viewModel.setAllMovieComments(allMovieComments);
                    this.viewModel.setMovieCommentPositionInTotalList(this.viewModel
                            .getAllMovieComments().indexOf(this.viewModel.getMovieComment()));
                    displayUserMovieCommentDetails();
                });
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
        this.viewBindings.userCommentEditionFragmentDeleteBtn.setOnClickListener(view ->
                Repository.getMovieCommentHandler()
                        .removeMovieComment(this.viewModel.getMovieCommentPositionInTotalList(), () -> {
                            new DeleteUserMovieCommentDialogFragment()
                                    .show(getActivity().getSupportFragmentManager(), "TAG");
                            Navigation.findNavController(view).popBackStack();
                        }));
        this.viewBindings.userCommentEditionFragmentSaveBtn.setOnClickListener(view -> {
            updateUserComment();
            Repository.getMovieCommentHandler()
                    .updateMovieComment(this.viewModel.getMovieCommentPositionInTotalList(),
                            this.viewModel.getMovieComment(), () -> {
                                new UpdateUserMovieCommentDialogFragment()
                                        .show(getActivity().getSupportFragmentManager(), "TAG");
                                Navigation.findNavController(view).popBackStack();
                            });
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
                        NavDirections action = UserCommentEditionFragmentDirections.actionGlobalUserProfileFragment();
                        Navigation.findNavController(viewBindings.getRoot()).navigate(action);
                        return true;
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}