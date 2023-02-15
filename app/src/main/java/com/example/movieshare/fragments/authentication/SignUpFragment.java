package com.example.movieshare.fragments.authentication;

import static com.example.movieshare.constants.AuthConstants.*;
import static com.example.movieshare.constants.UserConstants.USER_IMAGE_PROFILE_EXTENSION;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.MainActivity;
import com.example.movieshare.R;
import com.example.movieshare.databinding.FragmentSignUpBinding;
import com.example.movieshare.fragments.base.MovieBaseFragment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.User;
import com.example.movieshare.utils.InputValidator;
import com.example.movieshare.utils.UserUtils;
import com.example.movieshare.viewmodels.authentication.SignUpFragmentViewModel;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class SignUpFragment extends MovieBaseFragment {
    private FragmentSignUpBinding viewBindings;
    private SignUpFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.viewModel.setCameraLauncher(registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
                result -> {
                    if (Objects.nonNull(result)) {
                        viewBindings.signUpFragmentImg.setImageBitmap(result);
                        viewBindings.cameraButton.setVisibility(View.GONE);
                        this.viewModel.setProfilePictureSelected(true);
                    }}));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentSignUpBinding.inflate(inflater, container, false);
        this.configureMenuOptions(this.viewBindings.getRoot());
        initializeDataMembers();
        setListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(SignUpFragmentViewModel.class);
    }

    private void initializeDataMembers() {
        this.viewModel.setNavController(NavHostFragment.findNavController(this));
    }

    private void setListeners() {
        setCameraButtonOnClickListener();
        setRegisterButtonOnClickListener();
        setFirstNameEditTextOnKeyListener();
        setLastNameEditTextOnKeyListener();
        setEmailEditTextOnKeyListener();
        setPasswordEditTextOnKeyListener();
    }

    private void setCameraButtonOnClickListener() {
        this.viewBindings.cameraButton.setOnClickListener(view ->
                this.viewModel.getCameraLauncher().launch(null));
    }

    private void setRegisterButtonOnClickListener() {
        this.viewBindings.signUpFragmentRegisterBtn.setOnClickListener(view -> {
            UserUtils.setErrorIfFirstNameIsInvalid(this.viewBindings.signUpFragmentFirstNameInputEt);
            UserUtils.setErrorIfLastNameIsInvalid(this.viewBindings.signUpFragmentLastNameInputEt);
            UserUtils.setErrorIfEmailIsInvalid(this.viewBindings.signUpFragmentEmailInputEt);
            UserUtils.setErrorIfPasswordIsInvalid(this.viewBindings.signUpFragmentPasswordInputEt);
            if (isFormValid()) {
                registerIfValid(view);
            }
        });
    }

    private void registerIfValid(View view) {
        Repository.getRepositoryInstance().getAuthModel()
                .isEmailExists(this.viewBindings.signUpFragmentEmailInputEt.getText().toString(),
                        emailExist -> {
                            if (emailExist) {
                                this.viewBindings.signUpFragmentEmailInputEt
                                        .setError(REGISTER_EMAIL_ALREADY_EXIST);
                            } else {
                                this.registerUserProcess();
                            }
                        },
                        errorMessage -> Snackbar.make(view, errorMessage, Snackbar.LENGTH_SHORT).show());
    }

    private void registerUserProcess() {
        this.viewBindings.signUpFragmentRegisterBtn.setEnabled(false);
        User user = new User(this.viewBindings.signUpFragmentFirstNameInputEt.getText().toString(),
                this.viewBindings.signUpFragmentLastNameInputEt.getText().toString(),
                this.viewBindings.signUpFragmentEmailInputEt.getText().toString());

        if (!this.viewModel.isProfilePictureSelected()) {
            registerUser(user);
        } else {
            Bitmap profileImage = ((BitmapDrawable) this.viewBindings.signUpFragmentImg.getDrawable()).getBitmap();
            uploadUserProfilePhoto(profileImage, user);
        }
    }

    private void uploadUserProfilePhoto(Bitmap profileImage, User user) {
        Repository.getRepositoryInstance().getFirebaseModel().getUserExecutor()
                .uploadUserImage(profileImage, user.getEmail() + USER_IMAGE_PROFILE_EXTENSION, url -> {
                    if (Objects.nonNull(url)) {
                        user.setImageUrl(url);
                    }
                    registerUser(user);
                });
    }

    private void registerUser(User user) {
        Repository.getRepositoryInstance()
                .register(this::navigateToHomePageAfterRegister,
                        user,
                        this.viewBindings.signUpFragmentPasswordInputEt.getText().toString());
    }

    private void navigateToHomePageAfterRegister() {
        Intent intent = new Intent(this.getActivity(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private boolean isFormValid() {
        return (InputValidator.isFirstNameValid(this.viewBindings.signUpFragmentFirstNameInputEt.getText()) &&
                InputValidator.isLastNameValid(this.viewBindings.signUpFragmentLastNameInputEt.getText()) &&
                InputValidator.isEmailValid(this.viewBindings.signUpFragmentEmailInputEt.getText()) &&
                InputValidator.isPasswordValid(this.viewBindings.signUpFragmentPasswordInputEt.getText()));
    }

    private void setFirstNameEditTextOnKeyListener() {
        this.viewBindings.signUpFragmentFirstNameInputEt.setOnKeyListener((view, i, keyEvent) -> {
            UserUtils.setErrorIfFirstNameIsInvalid(this.viewBindings.signUpFragmentFirstNameInputEt);
            return false;
        });
    }

    private void setLastNameEditTextOnKeyListener() {
        this.viewBindings.signUpFragmentLastNameInputEt.setOnKeyListener((view, i, keyEvent) -> {
            UserUtils.setErrorIfLastNameIsInvalid(this.viewBindings.signUpFragmentLastNameInputEt);
            return false;
        });
    }

    private void setEmailEditTextOnKeyListener() {
        this.viewBindings.signUpFragmentEmailInputEt.setOnKeyListener((view, i, keyEvent) -> {
            UserUtils.setErrorIfEmailIsInvalid(this.viewBindings.signUpFragmentEmailInputEt);
            return false;
        });
    }

    private void setPasswordEditTextOnKeyListener() {
        this.viewBindings.signUpFragmentPasswordInputEt.setOnKeyListener((view, i, keyEvent) -> {
            UserUtils.setErrorIfPasswordIsInvalid(this.viewBindings.signUpFragmentPasswordInputEt);
            return false;
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
                menu.removeItem(R.id.logoutMenuItem);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    Navigation.findNavController(view).popBackStack();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}