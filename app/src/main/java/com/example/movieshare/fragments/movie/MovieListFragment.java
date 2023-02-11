package com.example.movieshare.fragments.movie;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.movieshare.R;
import com.example.movieshare.adapters.MovieAdapter;
import com.example.movieshare.databinding.FragmentMovieListBinding;
import com.example.movieshare.enums.LoadingState;
import com.example.movieshare.notifications.NotificationManager;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.dao.MovieApiCaller;
import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.repository.models.MovieApi;
import com.example.movieshare.repository.models.MovieApiList;
import com.example.movieshare.repository.models.MovieCategory;
import com.example.movieshare.viewmodels.movie.MovieListFragmentViewModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieListFragment extends Fragment {
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
        configureMenuOptions();
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
                        NavDirections action = MovieListFragmentDirections.actionGlobalUserProfileFragment();
                        Navigation.findNavController(viewBindings.getRoot()).navigate(action);
                        return true;
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}