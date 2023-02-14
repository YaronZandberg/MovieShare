package com.example.movieshare.fragments.movie;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.MovieCategoryAdapter;
import com.example.movieshare.databinding.FragmentMovieHomeBinding;
import com.example.movieshare.enums.LoadingState;
import com.example.movieshare.fragments.base.MovieBaseFragment;
import com.example.movieshare.notifications.NotificationManager;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.viewmodels.movie.MovieHomeFragmentViewModel;

public class MovieHomeFragment extends MovieBaseFragment {
    private FragmentMovieHomeBinding viewBindings;
    private MovieCategoryAdapter movieCategoryAdapter;
    private MovieHomeFragmentViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieHomeBinding.inflate(inflater, container, false);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setHasFixedSize(true);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieCategoryAdapter = new MovieCategoryAdapter(getLayoutInflater(),
                this.viewModel.getMovieCategories().getValue());
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setAdapter(this.movieCategoryAdapter);
        this.viewBindings.swipeRefresh.setOnRefreshListener(this::reloadMovieCategoryList);
        this.configureMenuOptions(this.viewBindings.getRoot());
        activateItemListListener();
        this.viewModel.getMovieCategories().observe(getViewLifecycleOwner(), movieCategories ->
                this.movieCategoryAdapter.setMovieItemList(this.viewModel.getMovieCategories().getValue()));
        NotificationManager.instance()
                .getEventMovieCategoryListLoadingState()
                .observe(getViewLifecycleOwner(),
                        loadingState -> this.viewBindings.swipeRefresh
                                .setRefreshing(loadingState == LoadingState.LOADING));
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(MovieHomeFragmentViewModel.class);
    }

    private void reloadMovieCategoryList() {
        Repository.getRepositoryInstance().refreshAllMovieCategories();
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
}