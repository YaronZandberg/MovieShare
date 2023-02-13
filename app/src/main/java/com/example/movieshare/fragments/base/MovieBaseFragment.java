package com.example.movieshare.fragments.base;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.movieshare.IntroActivity;
import com.example.movieshare.R;
import com.example.movieshare.fragments.movie.MovieHomeFragmentDirections;
import com.example.movieshare.repository.Repository;

import java.util.Objects;

public class MovieBaseFragment extends Fragment {

    protected void configureMenuOptions(View view) {
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.userCommentAdditionFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    Navigation.findNavController(view).popBackStack();
                    return true;
                } else {
                    if (Objects.nonNull(view)) {
                        if (menuItem.getItemId() == R.id.logoutMenuItem) {
                            Repository.getRepositoryInstance().getAuthModel().logout(() -> startIntroActivity());
                        } else {
                            NavDirections action = MovieHomeFragmentDirections.actionGlobalUserProfileFragment();
                            Navigation.findNavController(view).navigate(action);
                        }
                        return true;
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }

    private void startIntroActivity() {
        if (Objects.nonNull(getActivity())) {
            Intent introActivityIntent = new Intent(getActivity(), IntroActivity.class);
            startActivity(introActivityIntent);
            getActivity().finish();
        }
    }
}