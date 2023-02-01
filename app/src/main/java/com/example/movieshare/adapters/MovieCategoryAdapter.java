package com.example.movieshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.movieshare.R;
import com.example.movieshare.repository.models.MovieCategory;
import com.example.movieshare.viewholders.MovieCategoryViewHolder;
import com.example.movieshare.viewholders.MovieItemViewHolder;

import java.util.List;

public class MovieCategoryAdapter extends MovieItemAdapter<MovieCategory> {
    public MovieCategoryAdapter(LayoutInflater layoutInflater, List<MovieCategory> movieItemList){
        super(layoutInflater, movieItemList);
    }

    @NonNull
    @Override
    public MovieCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_list_row, parent, false);
        return new MovieCategoryViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder movieCategoryViewHolder, int position) {
        MovieCategory movieCategory = this.movieItemList.get(position);
        movieCategoryViewHolder.bindMovieItem(movieCategory);
    }
}
