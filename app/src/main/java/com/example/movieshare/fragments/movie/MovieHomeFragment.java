package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieHomeBinding.inflate(inflater, container, false);
        this.movieCategories = Repository.getMovieCategoryHandler().getAllMovieCategories();

        RecyclerView movieCategoriesRecyclerList = this.viewBindings.movieHomeFragmentMovieCategoriesList;
        movieCategoriesRecyclerList.setHasFixedSize(true);
        movieCategoriesRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieCategoryAdapter movieCategoryAdapter = new MovieCategoryAdapter(getLayoutInflater(), this.movieCategories);
        movieCategoriesRecyclerList.setAdapter(movieCategoryAdapter);

        movieCategoryAdapter.setOnItemClickListener(position -> {
            MovieHomeFragmentDirections
                    .ActionMovieHomeFragmentToMovieListFragment action =
                    MovieHomeFragmentDirections
                            .actionMovieHomeFragmentToMovieListFragment(position);
            Navigation.findNavController(viewBindings.movieHomeFragmentMovieCategoriesList).navigate(action);
        });

        return this.viewBindings.getRoot();
    }
}