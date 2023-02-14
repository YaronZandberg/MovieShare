package com.example.movieshare.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.movieshare.listeners.general.OnItemClickListener;
import com.example.movieshare.repository.models.MovieCategory;

public class MovieCategoryViewHolder extends MovieItemViewHolder<MovieCategory> {
    public MovieCategoryViewHolder(@NonNull View itemView, OnItemClickListener listener){
        super(itemView, listener);
    }

    @Override
    public void bindMovieItem(MovieCategory movieCategory) {
        this.movieItemNameTv.setText(movieCategory.getCategoryName());
    }
}
