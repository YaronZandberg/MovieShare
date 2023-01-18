package com.example.movieshare.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieshare.databinding.FragmentRegisterBinding;

public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding viewBindings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.viewBindings = FragmentRegisterBinding.inflate(inflater, container, false);
        this.viewBindings.registerFragmentRegisterBtn.setOnClickListener(view -> {
            NavDirections action = RegisterFragmentDirections
                    .actionRegisterFragmentToUserCommentListFragment();
            Navigation.findNavController(viewBindings.registerFragmentRegisterBtn).navigate(action);
            Toast.makeText(getContext(), "Register button has been clicked", Toast.LENGTH_LONG)
                    .show();
        });
        return this.viewBindings.getRoot();
    }
}