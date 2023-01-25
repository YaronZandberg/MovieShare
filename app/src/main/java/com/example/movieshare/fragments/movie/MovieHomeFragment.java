package com.example.movieshare.fragments.movie;

import android.os.Bundle;

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

import java.util.List;

public class MovieHomeFragment extends Fragment {
    private FragmentMovieHomeBinding viewBindings;
    private List<MovieCategory> movieCategories;
    private MovieCategoryAdapter movieCategoryAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Repository.getMovieCategoryHandler()
                .getAllMovieCategories(movieCategoryList ->
                        movieCategories = movieCategoryList
                );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieHomeBinding.inflate(inflater, container, false);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setHasFixedSize(true);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieCategoryAdapter = new MovieCategoryAdapter(getLayoutInflater(), this.movieCategories);
        this.viewBindings.movieHomeFragmentMovieCategoriesList.setAdapter(this.movieCategoryAdapter);
        this.movieCategoryAdapter.setOnItemClickListener(position -> {
            MovieHomeFragmentDirections
                    .ActionMovieHomeFragmentToMovieListFragment action =
                    MovieHomeFragmentDirections
                            .actionMovieHomeFragmentToMovieListFragment(position);
            Navigation.findNavController(viewBindings.movieHomeFragmentMovieCategoriesList).navigate(action);
        });
        return this.viewBindings.getRoot();
    }
}