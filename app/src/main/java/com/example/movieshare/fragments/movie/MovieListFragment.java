package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private MovieCategory movieCategory;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieCategoryPosition =
                MovieListFragmentArgs.fromBundle(getArguments()).getMovieCategoryPosition();
        this.movieCategory =
                Repository.getMovieCategoryHandler().getAllMovieCategories().get(this.movieCategoryPosition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieListBinding.inflate(inflater, container, false);
        this.movieList = Repository.getMovieHandler()
                .getAllMoviesByCategoryId(this.movieCategory.getCategoryId());

        RecyclerView movieRecyclerList = this.viewBindings.movieListFragmentMoviesList;
        movieRecyclerList.setHasFixedSize(true);
        movieRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        MovieAdapter movieAdapter = new MovieAdapter(getLayoutInflater(), this.movieList);
        movieRecyclerList.setAdapter(movieAdapter);

        movieAdapter.setOnItemClickListener(position -> {
            MovieListFragmentDirections
                    .ActionMovieListFragmentToMovieProfileFragment action =
                    MovieListFragmentDirections
                            .actionMovieListFragmentToMovieProfileFragment(position);
            Navigation.findNavController(viewBindings.movieListFragmentMoviesList).navigate(action);
        });

        return this.viewBindings.getRoot();
    }
}