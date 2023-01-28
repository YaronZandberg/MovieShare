package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.MovieAdapter;
import com.example.movieshare.databinding.FragmentMovieListBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.repository.models.MovieCategory;

import java.util.List;

public class MovieListFragment extends Fragment {
    private FragmentMovieListBinding viewBindings;
    private List<Movie> movieList;
    private Integer movieCategoryPosition;
    private List<MovieCategory> tempMovieCategories;
    private MovieCategory movieCategory;
    private MovieAdapter movieAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieCategoryPosition =
                MovieListFragmentArgs.fromBundle(getArguments()).getMovieCategoryPosition();
        Repository.getMovieCategoryHandler()
                .getAllMovieCategories(movieCategoryList ->
                        tempMovieCategories = movieCategoryList
                );
        this.movieCategory = tempMovieCategories.get(this.movieCategoryPosition);
        reloadList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieListBinding.inflate(inflater, container, false);
        this.viewBindings.movieListFragmentMoviesList.setHasFixedSize(true);
        this.viewBindings.movieListFragmentMoviesList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieAdapter = new MovieAdapter(getLayoutInflater(), this.movieList);
        this.viewBindings.movieListFragmentMoviesList.setAdapter(this.movieAdapter);
        this.movieAdapter.setOnItemClickListener(position -> {
            MovieListFragmentDirections
                    .ActionMovieListFragmentToMovieProfileFragment action =
                    MovieListFragmentDirections
                            .actionMovieListFragmentToMovieProfileFragment(position);
            Navigation.findNavController(viewBindings.movieListFragmentMoviesList).navigate(action);
        });
        return this.viewBindings.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadList();
    }

    private void reloadList() {
        Repository.getMovieHandler()
                .getAllMoviesByCategoryId(this.movieCategory.getCategoryId(), movieList -> {
                    this.movieList = movieList;
                    this.movieAdapter.setMovieList(this.movieList);
                });
    }
}