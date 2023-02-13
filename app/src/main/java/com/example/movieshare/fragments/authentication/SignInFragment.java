package com.example.movieshare.fragments.authentication;

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
import com.example.movieshare.utils.UserUtils;
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
            UserUtils.setErrorIfEmailIsInvalid(this.viewBindings.signInFragmentEmailInputEt);
            UserUtils.setErrorIfPasswordIsInvalid(this.viewBindings.signInFragmentPasswordInputEt);
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
        return (UserUtils.setErrorIfEmailIsInvalid(this.viewBindings.signInFragmentEmailInputEt) &&
                UserUtils.setErrorIfPasswordIsInvalid(this.viewBindings.signInFragmentPasswordInputEt));
    }

    private void setRegisterButtonOnClickListener() {
        this.viewBindings.signInFragmentRegisterBtn.setOnClickListener(view -> {
            NavDirections action = SignInFragmentDirections.actionSignInFragmentToSignUpFragment();
            navController.navigate(action);
        });
    }

    private void setEmailEditTextOnKeyListener() {
        this.viewBindings.signInFragmentEmailInputEt.setOnKeyListener((view, i, keyEvent) -> {
            UserUtils.setErrorIfEmailIsInvalid(this.viewBindings.signInFragmentEmailInputEt);
            return false;
        });
    }

    private void setPasswordEditTextOnKeyListener() {
        this.viewBindings.signInFragmentPasswordInputEt.setOnKeyListener((view, i, keyEvent) -> {
            UserUtils.setErrorIfPasswordIsInvalid(this.viewBindings.signInFragmentPasswordInputEt);
            return false;
        });
    }
}