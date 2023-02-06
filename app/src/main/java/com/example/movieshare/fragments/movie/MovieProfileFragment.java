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

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.R;
import com.example.movieshare.databinding.FragmentMovieProfileBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.viewmodels.movie.MovieProfileFragmentViewModel;

import java.util.Objects;

public class MovieProfileFragment extends Fragment {
    private FragmentMovieProfileBinding viewBindings;
    private Integer moviePosition;
    private String movieCategoryId;
    private MovieProfileFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.moviePosition = MovieProfileFragmentArgs.fromBundle(getArguments()).getMoviePosition();
        this.movieCategoryId = MovieProfileFragmentArgs.fromBundle(getArguments()).getMovieCategoryId();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieProfileBinding.inflate(inflater, container, false);
        initializeMovie();
        configureMenuOptions();
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(MovieProfileFragmentViewModel.class);
    }

    private void initializeMovie() {
        /*Repository.getRepositoryInstance().getLocalModel().getMovieHandler()
                .getAllMoviesByCategoryId(this.movieCategoryId, movieList -> {
            this.viewModel.setMovieList(movieList);
            this.viewModel.setMovie(this.viewModel.getMovieList().get(this.moviePosition));
            getMovieCategoryName();
        });*/
        Repository.getRepositoryInstance().getFirebaseModel()
                .getAllMoviesByCategoryId(this.movieCategoryId, movieList -> {
                    this.viewModel.setMovieList(movieList);
                    this.viewModel.setMovie(this.viewModel.getMovieList().get(this.moviePosition));
                    getMovieCategoryName();
                });
    }

    private void getMovieCategoryName() {
        if (Objects.nonNull(this.viewModel.getMovie())) {
            /*Repository.getRepositoryInstance().getLocalModel().getMovieCategoryHandler()
                    .getMovieCategoryById(this.viewModel.getMovie().getMovieCategoryId(), movieCategory -> {
                        this.viewModel.setMovieCategory(movieCategory);
                        this.viewModel.setMovieCategoryName(this.viewModel.getMovieCategory().getCategoryName());
                        displayMovieDetails();
                    });*/
            Repository.getRepositoryInstance().getFirebaseModel()
                    .getMovieCategoryById(this.viewModel.getMovie().getMovieCategoryId(), movieCategory -> {
                        this.viewModel.setMovieCategory(movieCategory);
                        this.viewModel.setMovieCategoryName(this.viewModel.getMovieCategory().getCategoryName());
                        displayMovieDetails();
                    });
        }
    }

    private void displayMovieDetails() {
        if (Objects.nonNull(this.viewModel.getMovie())) {
            this.viewBindings.movieProfileFragmentMovieNameInputEt.setText(this.viewModel.getMovie().getMovieName());
            this.viewBindings.movieProfileFragmentMovieCategoryInputEt.setText(this.viewModel.getMovieCategoryName());
            this.viewBindings.movieProfileFragmentMovieDescriptionInputEt.setText(this.viewModel.getMovie().getDescription());
            this.viewBindings.movieProfileFragmentMovieRatingInputEt.setText(this.viewModel.getMovie().getMovieRating());
            setUserCommentPropertiesState();
        }
    }

    private void setUserCommentPropertiesState() {
        this.viewBindings.movieProfileFragmentMovieNameInputEt.setFocusable(false);
        this.viewBindings.movieProfileFragmentMovieCategoryInputEt.setFocusable(true);
        this.viewBindings.movieProfileFragmentMovieDescriptionInputEt.setFocusable(true);
        this.viewBindings.movieProfileFragmentMovieRatingInputEt.setFocusable(true);
    }

    private void activateButtonsListeners() {
        this.viewBindings.movieProfileFragmentCommentsBtn.setOnClickListener(view -> {
            NavDirections action =
                    MovieProfileFragmentDirections
                            .actionMovieProfileFragmentToMovieCommentListFragment(this.viewModel.getMovie().getMovieId());
            Navigation.findNavController(view).navigate(action);
        });
    }

    private void configureMenuOptions() {
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    Navigation.findNavController(viewBindings.getRoot()).popBackStack();
                    return true;
                } else {
                    if (Objects.nonNull(viewBindings)) {
                        NavDirections action;
                        if (menuItem.getItemId() == R.id.userCommentAdditionFragment) {
                            action = MovieProfileFragmentDirections
                                    .actionMovieProfileFragmentToUserCommentAdditionFragment(viewModel.getMovie().getMovieName());
                        } else {
                            action = MovieProfileFragmentDirections.actionGlobalUserProfileFragment();
                        }
                        Navigation.findNavController(viewBindings.getRoot()).navigate(action);
                        return true;
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}