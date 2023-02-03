package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.R;
import com.example.movieshare.adapters.MovieCategoryAdapter;
import com.example.movieshare.databinding.FragmentMovieHomeBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.MovieCategory;
import com.example.movieshare.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieHomeFragment extends Fragment {
    private FragmentMovieHomeBinding viewBindings;
    private List<MovieCategory> movieCategories = new ArrayList<>();
    private MovieCategoryAdapter movieCategoryAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieHomeBinding.inflate(inflater, container, false);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setHasFixedSize(true);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieCategoryAdapter = new MovieCategoryAdapter(getLayoutInflater(), this.movieCategories);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setAdapter(this.movieCategoryAdapter);
        configureMenuOptions();
        activateItemListListener();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadMovieCategoryList();
    }

    private void reloadMovieCategoryList() {
        this.viewBindings.movieHomeFragmentProgressBar.setVisibility(View.VISIBLE);
        Repository.getMovieCategoryHandler()
                .getAllMovieCategories(movieCategoryList -> {
                    this.movieCategories = movieCategoryList;
                    this.movieCategoryAdapter.setMovieItemList(this.movieCategories);
                    MovieUtils.simulateSleeping();
                    this.viewBindings.movieHomeFragmentProgressBar.setVisibility(View.GONE);
                });
    }

    private void activateItemListListener() {
        this.movieCategoryAdapter.setOnItemClickListener(position -> {
            MovieHomeFragmentDirections
                    .ActionMovieHomeFragmentToMovieListFragment action =
                    MovieHomeFragmentDirections
                            .actionMovieHomeFragmentToMovieListFragment(position);
            Navigation.findNavController(viewBindings.movieHomeFragmentMovieCategoriesList).navigate(action);
        });
    }

    private void configureMenuOptions() {
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.userCommentAdditionFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    Navigation.findNavController(viewBindings.getRoot()).popBackStack();
                    return true;
                } else {
                    if (Objects.nonNull(viewBindings)) {
                        NavDirections action = MovieHomeFragmentDirections.actionGlobalUserProfileFragment();
                        Navigation.findNavController(viewBindings.getRoot()).navigate(action);
                        return true;
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}