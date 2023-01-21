package com.example.movieshare.fragments;

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
        this.viewBindings.loginFragmentLoginBtn.setOnClickListener(view -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToProfileFragment();
            Navigation.findNavController(viewBindings.loginFragmentLoginBtn).navigate(action);
            Toast.makeText(getContext(), "Login button has been clicked", Toast.LENGTH_LONG)
                    .show();
        });
        this.viewBindings.loginFragmentRegisterBtn.setOnClickListener(view -> {
            NavDirections action = LoginFragmentDirections.actionLoginFragmentToRegisterFragment();
            Navigation.findNavController(viewBindings.loginFragmentRegisterBtn).navigate(action);
            Toast.makeText(getContext(), "Register button has been clicked", Toast.LENGTH_LONG)
                    .show();
        });
        return this.viewBindings.getRoot();
    }
}