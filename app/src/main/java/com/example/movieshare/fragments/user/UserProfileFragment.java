package com.example.movieshare.fragments.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.movieshare.R;
import com.example.movieshare.databinding.FragmentUserProfileBinding;

public class UserProfileFragment extends Fragment {
    private FragmentUserProfileBinding viewBindings;
    // TODO: Delete the initialization of 0 after implementing getting userId from DB
    private Integer userId = 0;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentUserProfileBinding.inflate(inflater, container, false);
        configureMenuOptions();
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