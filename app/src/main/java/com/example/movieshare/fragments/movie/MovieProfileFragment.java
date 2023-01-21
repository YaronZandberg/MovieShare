package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentMovieProfileBinding;
import com.example.movieshare.fragments.user.UserProfileFragmentDirections;

public class MovieProfileFragment extends Fragment {
    private FragmentMovieProfileBinding viewBindings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieProfileBinding.inflate(inflater, container, false);
        this.viewBindings.movieProfileFragmentCommentsBtn.setOnClickListener(view -> {
            //NavDirections action = UserProfileFragmentDirections.actionProfileFragmentToUserCommentListFragment();
            //Navigation.findNavController(view).navigate(action);
        });
        return this.viewBindings.getRoot();
    }
}