package com.example.movieshare.fragments.user;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.movieshare.R;
import com.example.movieshare.databinding.FragmentUserProfileBinding;
import com.example.movieshare.fragments.base.MovieBaseFragment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.viewmodels.user.UserProfileFragmentViewModel;

import java.util.Objects;

public class UserProfileFragment extends MovieBaseFragment {
    private FragmentUserProfileBinding viewBindings;
    private String userId;
    private UserProfileFragmentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentUserProfileBinding.inflate(inflater, container, false);
        this.configureMenuOptions(this.viewBindings.getRoot());
        initializeUser();
        // TODO: Add loading of user image profile
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(UserProfileFragmentViewModel.class);
    }

    private void initializeUser() {
        this.userId = Repository.getRepositoryInstance().getAuthModel().getCurrentUserUid();
        Repository.getRepositoryInstance().getFirebaseModel().getUserExecutor()
                .getUserById(this.userId, user -> {
                    this.viewModel.setUser(user);
                    displayUserDetails();
                });
    }

    private void displayUserDetails() {
        if (Objects.nonNull(this.viewModel.getUser())) {
            this.viewBindings.userProfileFragmentFirstnameInputEt.setText(this.viewModel.getUser().getFirstName());
            this.viewBindings.userProfileFragmentLastnameInputEt.setText(this.viewModel.getUser().getLastName());
            this.viewBindings.userProfileFragmentEmailInputEt.setText(this.viewModel.getUser().getEmail());
            setUserProfilePropertiesState();
        }
    }

    private void setUserProfilePropertiesState() {
        this.viewBindings.userProfileFragmentFirstnameInputEt.setFocusable(false);
        this.viewBindings.userProfileFragmentLastnameInputEt.setFocusable(false);
        this.viewBindings.userProfileFragmentEmailInputEt.setFocusable(false);
    }

    private void activateButtonsListeners() {
        this.viewBindings.userProfileFragmentCommentsBtn.setOnClickListener(view -> {
            NavDirections action =
                    UserProfileFragmentDirections
                            .actionProfileFragmentToUserCommentListFragment(this.userId);
            Navigation.findNavController(view).navigate(action);
        });
        this.viewBindings.userProfileFragmentEditProfileBtn.setOnClickListener(view -> {
            NavDirections action =
                    UserProfileFragmentDirections
                            .actionUserProfileFragmentToUserProfileEditionFragment(this.userId);
            Navigation.findNavController(view).navigate(action);
        });
    }

    @Override
    protected void configureMenuOptions(View view) {
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.userCommentAdditionFragment);
                menu.removeItem(R.id.userProfileFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    Navigation.findNavController(view).popBackStack();
                    return true;
                } else {
                    if (Objects.nonNull(view)) {
                        Repository.getRepositoryInstance().getAuthModel().logout(() -> startIntroActivity());
                        return true;
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}