package com.example.movieshare.fragments.authentication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.movieshare.R;
import com.example.movieshare.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {
    private FragmentLoginBinding viewBindings;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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
        configureMenuOptions();

        return this.viewBindings.getRoot();
    }

    private void configureMenuOptions() {
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.userProfileFragment);
                menu.removeItem(R.id.userCommentAdditionFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    Navigation.findNavController(viewBindings.getRoot()).popBackStack();
                    return true;
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}