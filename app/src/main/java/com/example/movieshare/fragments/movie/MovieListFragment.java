package com.example.movieshare.fragments.movie;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.MovieAdapter;
import com.example.movieshare.databinding.FragmentMovieListBinding;
import com.example.movieshare.enums.LoadingState;
import com.example.movieshare.fragments.base.MovieBaseFragment;
import com.example.movieshare.notifications.NotificationManager;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.viewmodels.movie.MovieListFragmentViewModel;

import java.util.Objects;

public class MovieListFragment extends MovieBaseFragment {
    private FragmentMovieListBinding viewBindings;
    private Integer movieCategoryPosition;
    private MovieAdapter movieAdapter;
    private MovieListFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieCategoryPosition = MovieListFragmentArgs.fromBundle(getArguments()).getMovieCategoryPosition();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeMovieCategory();
        this.viewBindings = FragmentMovieListBinding.inflate(inflater, container, false);
        this.viewBindings.movieListFragmentMoviesList.setHasFixedSize(true);
        this.viewBindings.movieListFragmentMoviesList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieAdapter = new MovieAdapter(getLayoutInflater(), this.viewModel.getMovieList().getValue());
        this.viewBindings.movieListFragmentMoviesList.setAdapter(this.movieAdapter);
        this.viewBindings.swipeRefresh.setOnRefreshListener(this::initializeMovieCategory);
        this.configureMenuOptions(this.viewBindings.getRoot());
        activateItemListListener();
        this.viewModel.getMovieList().observe(getViewLifecycleOwner(), movies -> reloadMovieList());
        NotificationManager.instance()
                .getEventMovieListLoadingState()
                .observe(getViewLifecycleOwner(),
                        loadingState -> this.viewBindings.swipeRefresh
                                .setRefreshing(loadingState == LoadingState.LOADING));
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(MovieListFragmentViewModel.class);
    }

    private void initializeMovieCategory() {
        Repository.getRepositoryInstance().refreshAllMovieCategories();
        this.viewModel.setMovieCategory(this.viewModel.getAllMovieCategories().getValue()
                .get(this.movieCategoryPosition));
        Repository.getRepositoryInstance().refreshAllMovies();
    }

    private void reloadMovieList() {
        if (Objects.nonNull(this.viewModel.getMovieList().getValue())) {
            Repository.getRepositoryInstance().getLocalModel().getMovieHandler()
                    .getAllMoviesByCategoryId(this.viewModel.getMovieCategory().getCategoryId(),
                            movieList -> this.movieAdapter.setMovieItemList(movieList));
        }
    }

    private void activateItemListListener() {
        this.movieAdapter.setOnItemClickListener(position -> {
            MovieListFragmentDirections
                    .ActionMovieListFragmentToMovieProfileFragment action =
                    MovieListFragmentDirections
                            .actionMovieListFragmentToMovieProfileFragment(position,
                                    this.viewModel.getMovieCategory().getCategoryId());
            Navigation.findNavController(viewBindings.movieListFragmentMoviesList).navigate(action);
        });
    }
}