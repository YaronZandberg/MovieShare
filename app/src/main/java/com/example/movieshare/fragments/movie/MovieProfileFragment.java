package com.example.movieshare.fragments.movie;

import android.os.Bundle;

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

public class MovieProfileFragment extends Fragment {
    private FragmentMovieProfileBinding viewBindings;
    private Integer moviePosition;
    private Movie movie;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.moviePosition = MovieProfileFragmentArgs.fromBundle(getArguments()).getMoviePosition();
        Repository.getMovieHandler().getAllMovies(movieList ->
                this.movie = movieList.get(this.moviePosition)
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieProfileBinding.inflate(inflater, container, false);
        this.viewBindings.movieProfileFragmentCommentsBtn.setOnClickListener(view -> {
            NavDirections action =
                    MovieProfileFragmentDirections
                            .actionMovieProfileFragmentToMovieCommentListFragment(this.moviePosition);
            Navigation.findNavController(view).navigate(action);
        });
        return this.viewBindings.getRoot();
    }
}