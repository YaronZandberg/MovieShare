package com.example.movieshare.fragments.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentUserProfileBinding;

public class UserProfileFragment extends Fragment {
    private FragmentUserProfileBinding viewBindings;
    // TODO: Delete the initialization of 0 after implementing getting userId from DB
    private Integer userId = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentUserProfileBinding.inflate(inflater, container, false);
        // Get userId from DB according username
        //this.userId =

        this.viewBindings.userProfileFragmentCommentsBtn.setOnClickListener(view -> {
            NavDirections action =
                    UserProfileFragmentDirections
                            .actionProfileFragmentToUserCommentListFragment(this.userId);
            Navigation.findNavController(view).navigate(action);
        });
        return this.viewBindings.getRoot();
    }
}