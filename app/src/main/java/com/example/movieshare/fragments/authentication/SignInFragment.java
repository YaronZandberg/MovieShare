package com.example.movieshare.fragments.authentication;

import static com.example.movieshare.constants.AuthConstants.*;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentSignInBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.InputValidator;
import com.google.android.material.snackbar.Snackbar;

public class SignInFragment extends Fragment {
    private FragmentSignInBinding viewBindings;
    private NavController navController;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentSignInBinding.inflate(inflater, container, false);
        initializeDataMembers();
        setListeners();
        return this.viewBindings.getRoot();
    }

    private void initializeDataMembers() {
        this.navController = NavHostFragment.findNavController(this);
    }

    private void setListeners() {
        setLoginButtonOnClickListener();
        setRegisterButtonOnClickListener();
        setEmailEditTextOnKeyListener();
        setPasswordEditTextOnKeyListener();
    }

    private void setLoginButtonOnClickListener() {
        this.viewBindings.signInFragmentLoginBtn.setOnClickListener(view -> {
            setErrorIfEmailIsInvalid();
            setErrorIfPasswordIsInvalid();
            if(isFormValid()) {
                this.viewBindings.signInFragmentLoginBtn.setEnabled(false);
                this.viewBindings.signInFragmentRegisterBtn.setEnabled(false);
                Repository.getRepositoryInstance().getAuthModel().login(
                        this.viewBindings.signInFragmentEmailInputEt.getText().toString(),
                        this.viewBindings.signInFragmentPasswordInputEt.getText().toString(),
                        () -> {
                            NavDirections action = SignInFragmentDirections.actionSignInFragmentToNavGraph();
                            navController.navigate(action);
                        },
                        errorMessage -> {
                            Snackbar.make(view, errorMessage, Snackbar.LENGTH_SHORT).show();
                            this.viewBindings.signInFragmentLoginBtn.setEnabled(true);
                            this.viewBindings.signInFragmentRegisterBtn.setEnabled(true);
                        });
            }
        });
    }

    private boolean isFormValid() {
        return (setErrorIfEmailIsInvalid() && setErrorIfPasswordIsInvalid());
    }

    private void setRegisterButtonOnClickListener() {
        this.viewBindings.signInFragmentRegisterBtn.setOnClickListener(view -> {
            NavDirections action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment();
            navController.navigate(action);
        });
    }

    private void setEmailEditTextOnKeyListener() {
        this.viewBindings.signInFragmentEmailInputEt.setOnKeyListener((view, i, keyEvent) -> {
            setErrorIfEmailIsInvalid();
            return false;
        });
    }

    private void setPasswordEditTextOnKeyListener() {
        this.viewBindings.signInFragmentPasswordInputEt.setOnKeyListener((view, i, keyEvent) -> {
            setErrorIfPasswordIsInvalid();
            return false;
        });
    }

    private boolean setErrorIfEmailIsInvalid() {
        if (!InputValidator.isEmailValid(this.viewBindings.signInFragmentEmailInputEt.getText())) {
            this.viewBindings.signInFragmentEmailInputEt.setError(LOGIN_INVALID_EMAIL);
            return false;
        } else {
            this.viewBindings.signInFragmentEmailInputEt.setError(null);
            return true;
        }
    }

    private boolean setErrorIfPasswordIsInvalid() {
        if (!InputValidator.isPasswordValid(this.viewBindings.signInFragmentPasswordInputEt.getText())) {
            this.viewBindings.signInFragmentPasswordInputEt.setError(LOGIN_INVALID_PASSWORD);
            return false;
        } else {
            this.viewBindings.signInFragmentPasswordInputEt.setError(null);
            return true;
        }
    }
}