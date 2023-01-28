package com.example.movieshare.fragments.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieshare.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding viewBindings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.viewBindings = FragmentLoginBinding.inflate(inflater, container, false);
        this.viewBindings.loginFragmentLoginBtn.setOnClickListener(view ->
                Toast.makeText(getContext(), "Login button has been clicked", Toast.LENGTH_LONG)
                        .show());
        this.viewBindings.loginFragmentRegisterBtn.setOnClickListener(view -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
            Navigation.findNavController(view).navigate(action);
            Toast.makeText(getContext(), "Register button has been clicked", Toast.LENGTH_LONG)
                    .show();
        });
        this.viewBindings.loginFragmentUserBtn.setOnClickListener(view -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToUserProfileFragment();
            Navigation.findNavController(view).navigate(action);
            Toast.makeText(getContext(), "User button has been clicked", Toast.LENGTH_LONG)
                    .show();
        });
        this.viewBindings.loginFragmentHomeBtn.setOnClickListener(view -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToMovieHomeFragment();
            Navigation.findNavController(view).navigate(action);
            Toast.makeText(getContext(), "Home button has been clicked", Toast.LENGTH_LONG)
                    .show();
        });
        this.viewBindings.testCategoryBtn.setOnClickListener(view -> {
            NavDirections action = LoginFragmentDirections
                    .actionLoginFragmentToTestAddMovieCategoryFragment();
            Navigation.findNavController(view).navigate(action);
            Toast.makeText(getContext(), "Category Test has been started", Toast.LENGTH_LONG)
                    .show();
        });
        this.viewBindings.testMovieBtn.setOnClickListener(view -> {
            NavDirections action = LoginFragmentDirections
                    .actionLoginFragmentToTestAddMovieFragment();
            Navigation.findNavController(view).navigate(action);
            Toast.makeText(getContext(), "Movie Test has been started", Toast.LENGTH_LONG)
                    .show();
        });
        return this.viewBindings.getRoot();
    }
}