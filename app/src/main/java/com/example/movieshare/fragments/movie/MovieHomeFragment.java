package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.MovieCategoryAdapter;
import com.example.movieshare.databinding.FragmentMovieHomeBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.MovieCategory;

import java.util.ArrayList;
import java.util.List;

public class MovieHomeFragment extends Fragment {
    private FragmentMovieHomeBinding viewBindings;
    private List<MovieCategory> movieCategories = new ArrayList<>();
    private MovieCategoryAdapter movieCategoryAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        reloadMovieCategoryList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieHomeBinding.inflate(inflater, container, false);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setHasFixedSize(true);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieCategoryAdapter = new MovieCategoryAdapter(getLayoutInflater(), this.movieCategories);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setAdapter(this.movieCategoryAdapter);
        activateItemListListener();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadMovieCategoryList();
    }

    private void reloadMovieCategoryList() {
        Repository.getMovieCategoryHandler()
                .getAllMovieCategories(movieCategoryList -> {
                    this.movieCategories = movieCategoryList;
                    this.movieCategoryAdapter.setMovieItemList(this.movieCategories);
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
}