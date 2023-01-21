package com.example.movieshare.fragments.profile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {
    private FragmentProfileBinding viewBindings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentProfileBinding.inflate(inflater, container, false);
        this.viewBindings.profileFragmentCommentsBtn.setOnClickListener(view -> {
            NavDirections action = ProfileFragmentDirections.actionProfileFragmentToUserCommentListFragment();
            Navigation.findNavController(view).navigate(action);
        });
        return this.viewBindings.getRoot();
    }
}