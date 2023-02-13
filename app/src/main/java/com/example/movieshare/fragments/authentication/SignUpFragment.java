package com.example.movieshare.fragments.authentication;

import static com.example.movieshare.constants.AuthConstants.*;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentSignUpBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.User;
import com.example.movieshare.utils.InputValidator;
import com.example.movieshare.utils.UserUtils;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class SignUpFragment extends Fragment {
    private FragmentSignUpBinding viewBindings;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentSignUpBinding.inflate(inflater, container, false);
        initializeDataMembers();
        setListeners();
        return this.viewBindings.getRoot();
    }

    private void initializeDataMembers() {
        this.navController = NavHostFragment.findNavController(this);
    }

    private void setListeners() {
        setRegisterButtonOnClickListener();
        setFirstNameEditTextOnKeyListener();
        setLastNameEditTextOnKeyListener();
        setEmailEditTextOnKeyListener();
        setPasswordEditTextOnKeyListener();
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
                                this.register(view);
                            }
                        },
                        errorMessage -> Snackbar.make(view, errorMessage, Snackbar.LENGTH_SHORT).show());
    }

    private void register(View view) {
        this.viewBindings.signUpFragmentRegisterBtn.setEnabled(false);
        User user = new User(this.viewBindings.signUpFragmentFirstNameInputEt.getText().toString(),
                this.viewBindings.signUpFragmentLastNameInputEt.getText().toString(),
                this.viewBindings.signUpFragmentEmailInputEt.getText().toString());
        //Bitmap profileImage = ((BitmapDrawable) this.viewBindings.signUpFragmentImg.getDrawable()).getBitmap();
        Bitmap profileImage = null;
        if (Objects.isNull(profileImage)) {
            Repository.getRepositoryInstance()
                    .register(this::navigateToHomePageAfterRegister,
                            user,
                            this.viewBindings.signUpFragmentPasswordInputEt.getText().toString());
        } else {
            Repository.getRepositoryInstance().getFirebaseModel().getUserExecutor()
                    .uploadUserImage(profileImage, user.getEmail() + ".jpg", url -> {
                        user.setImageUrl(url);
                        Repository.getRepositoryInstance()
                                .register(this::navigateToHomePageAfterRegister,
                                        user,
                                        this.viewBindings.signUpFragmentPasswordInputEt.getText().toString());
                    });
        }
    }

    private void navigateToHomePageAfterRegister() {
        NavDirections action = SignUpFragmentDirections.actionSignUpFragmentToNavGraph();
        navController.navigate(action);
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
}