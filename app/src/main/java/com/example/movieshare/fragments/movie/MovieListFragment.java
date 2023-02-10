package com.example.movieshare.fragments.movie;

import android.content.Context;
import android.os.Bundle;

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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.R;
import com.example.movieshare.adapters.MovieAdapter;
import com.example.movieshare.databinding.FragmentMovieListBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.MovieUtils;
import com.example.movieshare.viewmodels.movie.MovieListFragmentViewModel;

import java.util.Objects;

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
        this.viewBindings = FragmentMovieListBinding.inflate(inflater, container, false);
        this.viewBindings.movieListFragmentMoviesList.setHasFixedSize(true);
        this.viewBindings.movieListFragmentMoviesList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieAdapter = new MovieAdapter(getLayoutInflater(), this.viewModel.getMovieList());
        this.viewBindings.movieListFragmentMoviesList.setAdapter(this.movieAdapter);
        configureMenuOptions();
        activateItemListListener();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(MovieListFragmentViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeMovieCategory();
    }

    private void initializeMovieCategory() {
        /*Repository.getRepositoryInstance().getLocalModel().getMovieCategoryHandler()
                .getAllMovieCategories(movieCategoryList -> {
                    this.viewModel.setAllMovieCategories(movieCategoryList);
                    this.viewModel.setMovieCategory(this.viewModel.getAllMovieCategories()
                            .get(this.movieCategoryPosition));
                    reloadMovieList();
                });*/
        Repository.getRepositoryInstance().getFirebaseModel().getMovieCategoryExecutor()
                .getAllMovieCategories(movieCategoryList -> {
                    this.viewModel.setAllMovieCategories(movieCategoryList);
                    this.viewModel.setMovieCategory(this.viewModel.getAllMovieCategories()
                            .get(this.movieCategoryPosition));
                    reloadMovieList();
                });
    }

    private void reloadMovieList() {
        if (Objects.nonNull(this.viewModel.getMovieCategory())) {
            this.viewBindings.movieListFragmentProgressBar.setVisibility(View.VISIBLE);
            /*Repository.getRepositoryInstance().getLocalModel().getMovieHandler()
                    .getAllMoviesByCategoryId(this.viewModel.getMovieCategory().getCategoryId(), movieList -> {
                        this.viewModel.setMovieList(movieList);
                        this.movieAdapter.setMovieItemList(this.viewModel.getMovieList());
                        MovieUtils.simulateSleeping();
                        this.viewBindings.movieListFragmentProgressBar.setVisibility(View.GONE);
                    });*/
            Repository.getRepositoryInstance().getFirebaseModel().getMovieExecutor()
                    .getAllMoviesByCategoryId(this.viewModel.getMovieCategory().getCategoryId(), movieList -> {
                        this.viewModel.setMovieList(movieList);
                        this.movieAdapter.setMovieItemList(this.viewModel.getMovieList());
                        MovieUtils.simulateSleeping();
                        this.viewBindings.movieListFragmentProgressBar.setVisibility(View.GONE);
                    });
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