package com.example.movieshare.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.movieshare.listeners.OnItemClickListener;
import com.example.movieshare.repository.models.Movie;
import com.squareup.picasso.Picasso;


public class MovieViewHolder extends MovieItemViewHolder<Movie> {
    public MovieViewHolder(@NonNull View itemView, OnItemClickListener listener){
        super(itemView, listener);
    }

    @Override
    public void bindMovieItem(Movie movie) {
        Picasso.get().load(movie.getMovieImg()).into(this.movieItemNameImg);
        this.movieItemNameTv.setText(movie.getMovieName());
        this.movieItemRatingTv.setText(movie.getMovieRating());
    }
}
