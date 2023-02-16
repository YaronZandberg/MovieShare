package com.example.movieshare.fragments.authentication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
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
import com.example.movieshare.databinding.FragmentSignInBinding;
import com.example.movieshare.fragments.base.MovieBaseFragment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.UserUtils;
import com.example.movieshare.viewmodels.authentication.SignInFragmentViewModel;
import com.google.android.material.snackbar.Snackbar;

public class SignInFragment extends MovieBaseFragment {
    private FragmentSignInBinding viewBindings;
    private SignInFragmentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentSignInBinding.inflate(inflater, container, false);
        this.configureMenuOptions(this.viewBindings.getRoot());
        initializeDataMembers();
        setListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(SignInFragmentViewModel.class);
    }

    private void initializeDataMembers() {
        this.viewModel.setNavController(NavHostFragment.findNavController(this));
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
                            Intent intent = new Intent(this.getActivity(), MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
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
            this.viewModel.getNavController().navigate(action);
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