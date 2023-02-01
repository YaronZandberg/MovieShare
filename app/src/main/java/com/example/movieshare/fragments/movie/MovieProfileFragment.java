package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.databinding.FragmentMovieProfileBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.repository.models.MovieCategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieProfileFragment extends Fragment {
    private FragmentMovieProfileBinding viewBindings;
    private Integer moviePosition;
    private Integer movieCategoryId;
    private List<Movie> movieList = new ArrayList<>();
    private Movie movie;
    private MovieCategory movieCategory;
    private String movieCategoryName;

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
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    private void initializeMovie() {
        Repository.getMovieHandler().getAllMoviesByCategoryId(this.movieCategoryId, movieList -> {
            this.movieList = movieList;
            this.movie = this.movieList.get(this.moviePosition);
            getMovieCategoryName();
        });
    }

    private void getMovieCategoryName() {
        if (Objects.nonNull(this.movie)) {
            Repository.getMovieCategoryHandler()
                    .getMovieCategoryById(this.movie.getMovieCategoryId(), movieCategory -> {
                        this.movieCategory = movieCategory;
                        this.movieCategoryName = this.movieCategory.getCategoryName();
                        displayMovieDetails();
                    });
        }
    }

    private void displayMovieDetails() {
        if (Objects.nonNull(this.movie)) {
            this.viewBindings.movieProfileFragmentMovieNameInputEt.setText(this.movie.getMovieName());
            this.viewBindings.movieProfileFragmentMovieCategoryInputEt.setText(this.movieCategoryName);
            this.viewBindings.movieProfileFragmentMovieDescriptionInputEt.setText(this.movie.getDescription());
            this.viewBindings.movieProfileFragmentMovieRatingInputEt.setText(this.movie.getMovieRating());
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
                            .actionMovieProfileFragmentToMovieCommentListFragment(this.movie.getMovieId());
            Navigation.findNavController(view).navigate(action);
        });
    }
}