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
        this.movieItemNameTv = itemView.findViewById(R.id.item_list_row_name_tv);
        this.movieItemRatingTv = itemView.findViewById(R.id.item_list_row_rating_tv);
        this.movieItemDescriptionTv = itemView.findViewById(R.id.item_list_row_comment_tv);
        this.movieItemImg = itemView.findViewById(R.id.item_list_row_img);
    }

    @Override
    public void bindMovieItem(Movie movie) {
        if (Objects.nonNull(movie.getImageUrl(true))) {
            Picasso.get().load(movie.getImageUrl(true))
                    .placeholder(R.drawable.movie_default_image)
                    .into(this.movieItemImg);
        } else {
            this.movieItemImg.setImageResource(R.drawable.movie_default_image);
        }
        this.movieItemNameTv.setText(movie.getMovieName());
        this.movieItemRatingTv.setText(movie.getMovieRating());
    }
}
