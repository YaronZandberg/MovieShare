package com.example.movieshare.fragments.movie;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import com.example.movieshare.R;
import com.example.movieshare.constants.Categories;
import com.example.movieshare.databinding.FragmentMovieProfileBinding;
import com.example.movieshare.fragments.base.MovieBaseFragment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.RoundedTransformation;
import com.example.movieshare.viewmodels.movie.MovieProfileFragmentViewModel;
import com.google.android.material.chip.Chip;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MovieProfileFragment extends MovieBaseFragment {
    private FragmentMovieProfileBinding viewBindings;
    private Integer moviePosition;
    private String movieCategoryId;
    private MovieProfileFragmentViewModel viewModel;
    private Categories categories = new Categories();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.moviePosition = MovieProfileFragmentArgs.fromBundle(getArguments()).getMoviePosition();
        this.movieCategoryId = MovieProfileFragmentArgs.fromBundle(getArguments()).getMovieCategoryId();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeMovie();
        this.viewBindings = FragmentMovieProfileBinding.inflate(inflater, container, false);
        this.configureMenuOptions(this.viewBindings.getRoot());
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(MovieProfileFragmentViewModel.class);
    }

    private void initializeMovie() {
        Repository.getRepositoryInstance().getLocalModel().getMovieHandler()
                .getAllMoviesByCategoryId(this.movieCategoryId, movieList -> {
                    this.viewModel.setMovieList(movieList);
                    this.viewModel.setMovie(this.viewModel.getMovieList().get(this.moviePosition));
                    ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(this.viewModel.getMovie().getMovieName());
                    getMovieCategoryName();
                });
    }

    private void getMovieCategoryName() {
        if (Objects.nonNull(this.viewModel.getMovie())) {
            Repository.getRepositoryInstance().getLocalModel().getMovieCategoryHandler()
                    .getMovieCategoryById(this.viewModel.getMovie().getMovieCategoryId(), movieCategory -> {
                        this.viewModel.setMovieCategory(movieCategory);
                        this.viewModel.setMovieCategoryName(this.viewModel.getMovieCategory().getCategoryName());
                        this.viewModel.setMovieCategoryImage(categories.getImageByName(movieCategory.getCategoryName()));
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
            loadImage(this.viewModel.getMovie().getImageUrl(true), R.drawable.movie_default_image, this.viewBindings.movieProfileFragmentImg);
            loadChipImage(this.viewModel.getMovieCategoryImage(), this.viewBindings.movieProfileFragmentMovieCategoryInputEt);
            setUserCommentPropertiesState();
        }
    }

    private void loadImage(String Url, Integer placeholder, ImageView item) {
        if (Objects.nonNull(Url)) {
            Picasso.get().load(Url).transform(new RoundedTransformation(30, 0))
                    .placeholder(placeholder)
                    .into(item);
        } else {
            item.setImageResource(placeholder);
        }
    }

    private void loadChipImage(Integer placeholder, Chip item) {
        ImageView chip = new ImageView(item.getContext());
        chip.setImageResource(placeholder);
        item.setChipIcon(chip.getDrawable());
        item.setChipIconTintResource(R.color.black);
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

    @Override
    protected void configureMenuOptions(View view) {
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
                    if (Objects.nonNull(view)) {
                        if (menuItem.getItemId() == R.id.userCommentAdditionFragment) {
                            NavDirections action = MovieProfileFragmentDirections
                                    .actionMovieProfileFragmentToUserCommentAdditionFragment(viewModel.getMovie().getMovieName());
                            Navigation.findNavController(viewBindings.getRoot()).navigate(action);
                        } else if (menuItem.getItemId() == R.id.userProfileFragment) {
                            NavDirections action = MovieProfileFragmentDirections.actionGlobalUserProfileFragment();
                            Navigation.findNavController(viewBindings.getRoot()).navigate(action);
                        } else {
                            Repository.getRepositoryInstance().getAuthModel().logout(() -> startIntroActivity());
                        }
                        return true;
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}