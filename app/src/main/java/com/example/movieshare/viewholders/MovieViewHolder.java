package com.example.movieshare.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.movieshare.R;
import com.example.movieshare.listeners.general.OnItemClickListener;
import com.example.movieshare.repository.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MovieViewHolder extends MovieItemViewHolder<Movie> {
    public MovieViewHolder(@NonNull View itemView, OnItemClickListener listener){
        super(itemView, listener);
    }

    @Override
    public void bindMovieItem(Movie movie) {
        loadUserProfileImage(movie);
        this.movieItemNameTv.setText(movie.getMovieName());
        this.movieItemRatingTv.setText(movie.getMovieRating());
    }

    private void loadUserProfileImage(Movie movie) {
        if (Objects.nonNull(movie.getImageUrl())) {
            Picasso.get().load(movie.getImageUrl())
                    .placeholder(R.drawable.movie_default_image)
                    .into(this.movieItemImg);
        } else {
            this.movieItemImg.setImageResource(R.drawable.movie_default_image);
        }
    }
}
