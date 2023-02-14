package com.example.movieshare.fragments.user;

import static com.example.movieshare.constants.UserConstants.USER_IMAGE_PROFILE_EXTENSION;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.R;
import com.example.movieshare.databinding.FragmentUserProfileEditionBinding;
import com.example.movieshare.fragments.dialogs.UpdateUserProfileDialogFragment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.InputValidator;
import com.example.movieshare.utils.UserUtils;
import com.example.movieshare.viewmodels.user.UserProfileEditionFragmentViewModel;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class UserProfileEditionFragment extends UserCommentFormFragment {
    private FragmentUserProfileEditionBinding viewBindings;
    private String userId;
    private UserProfileEditionFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userId = UserProfileEditionFragmentArgs.fromBundle(getArguments()).getUserId();
        this.viewModel.setCameraLauncher(registerForActivityResult(new ActivityResultContracts.TakePicturePreview(),
                result -> {
                    if (Objects.nonNull(result)) {
                        viewBindings.userProfileEditionFragmentImg.setImageBitmap(result);
                        this.viewModel.setProfilePictureSelected(true);
                    }}));
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentUserProfileEditionBinding.inflate(inflater, container, false);
        this.configureMenuOptions(this.viewBindings.getRoot());
        initializeUser();
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(UserProfileEditionFragmentViewModel.class);
    }

    private void initializeUser() {
        Repository.getRepositoryInstance().getFirebaseModel().getUserExecutor()
                .getUserById(this.userId, user -> {
                    this.viewModel.setUser(user);
                    displayUserMovieCommentDetails();
                });
    }

    @Override
    protected void displayUserMovieCommentDetails() {
        if (Objects.nonNull(this.viewModel.getUser())) {
            this.viewBindings.userProfileEditionFragmentFirstnameInputEt.setText(this.viewModel.getUser().getFirstName());
            this.viewBindings.userProfileEditionFragmentLastnameInputEt.setText(this.viewModel.getUser().getLastName());
            loadUserProfileImage();
            setUserCommentPropertiesState();
        }
    }

    private void loadUserProfileImage() {
        if (Objects.nonNull(this.viewModel.getUser().getImageUrl())) {
            Picasso.get().load(this.viewModel.getUser().getImageUrl())
                    .placeholder(R.drawable.avatar)
                    .into(this.viewBindings.userProfileEditionFragmentImg);
        } else {
            this.viewBindings.userProfileEditionFragmentImg.setImageResource(R.drawable.avatar);
        }
    }

    @Override
    protected void setUserCommentPropertiesState() {
        this.viewBindings.userProfileEditionFragmentFirstnameInputEt.setFocusable(true);
        this.viewBindings.userProfileEditionFragmentLastnameInputEt.setFocusable(true);
    }

    @Override
    protected void activateButtonsListeners() {
        setFirstNameEditTextOnKeyListener();
        setLastNameEditTextOnKeyListener();
        setProfileImageViewOnClickListener();
        this.viewBindings.userProfileEditionFragmentSaveBtn.setOnClickListener(view -> {
            UserUtils.setErrorIfFirstNameIsInvalid(this.viewBindings.userProfileEditionFragmentFirstnameInputEt);
            UserUtils.setErrorIfLastNameIsInvalid(this.viewBindings.userProfileEditionFragmentLastnameInputEt);
            if(isFormValid()) {
                saveProfile(view);
            }
        });
    }

    private void saveProfile(View view) {
        this.viewBindings.userProfileEditionFragmentSaveBtn.setEnabled(false);
        this.viewModel.getUser().setFirstName(this.viewBindings.userProfileEditionFragmentFirstnameInputEt.getText().toString());
        this.viewModel.getUser().setLastName(this.viewBindings.userProfileEditionFragmentLastnameInputEt.getText().toString());

        if (!this.viewModel.isProfilePictureSelected()) {
            updateUser(view);
        } else {
            Bitmap profileImage = ((BitmapDrawable) this.viewBindings.userProfileEditionFragmentImg.getDrawable()).getBitmap();
            uploadUserProfilePhoto(profileImage, view);
        }
    }

    private void uploadUserProfilePhoto(Bitmap profileImage, View view) {
        Repository.getRepositoryInstance().getFirebaseModel().getUserExecutor()
                .uploadUserImage(profileImage, this.viewModel.getUser().getEmail() + USER_IMAGE_PROFILE_EXTENSION, url -> {
                    if (Objects.nonNull(url)) {
                        this.viewModel.getUser().setImageUrl(url);
                    }
                    updateUser(view);
                });
    }

    private void updateUser(View view) {
        Repository.getRepositoryInstance().getFirebaseModel().getUserExecutor()
                .updateUser(this.viewModel.getUser(), () -> {
                    new UpdateUserProfileDialogFragment()
                            .show(getActivity().getSupportFragmentManager(), "TAG");
                    Navigation.findNavController(view).popBackStack();
                });
    }

    private boolean isFormValid() {
        return (InputValidator.isFirstNameValid(this.viewBindings.userProfileEditionFragmentFirstnameInputEt.getText()) &&
                InputValidator.isLastNameValid(this.viewBindings.userProfileEditionFragmentLastnameInputEt.getText()));
    }

    private void setProfileImageViewOnClickListener() {
        this.viewBindings.userProfileEditionFragmentImgBtn.setOnClickListener(view ->
                this.viewModel.getCameraLauncher().launch(null));
    }

    private void setFirstNameEditTextOnKeyListener() {
        this.viewBindings.userProfileEditionFragmentFirstnameInputEt.setOnKeyListener((view, i, keyEvent) -> {
            UserUtils.setErrorIfFirstNameIsInvalid(this.viewBindings.userProfileEditionFragmentFirstnameInputEt);
            return false;
        });
    }

    private void setLastNameEditTextOnKeyListener() {
        this.viewBindings.userProfileEditionFragmentLastnameInputEt.setOnKeyListener((view, i, keyEvent) -> {
            UserUtils.setErrorIfLastNameIsInvalid(this.viewBindings.userProfileEditionFragmentLastnameInputEt);
            return false;
        });
    }
}