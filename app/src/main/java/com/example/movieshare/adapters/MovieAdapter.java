package com.example.movieshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.movieshare.R;
import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.viewholders.MovieItemViewHolder;
import com.example.movieshare.viewholders.MovieViewHolder;

import java.util.List;

public class MovieAdapter extends MovieItemAdapter<Movie> {
    public MovieAdapter(LayoutInflater layoutInflater, List<Movie> movieItemList){
        super(layoutInflater, movieItemList);
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_list_row, parent, false);
        return new MovieViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder movieViewHolder, int position) {
        Movie movie = this.movieItemList.get(position);
        movieViewHolder.bindMovieItem(movie);
    }
}
