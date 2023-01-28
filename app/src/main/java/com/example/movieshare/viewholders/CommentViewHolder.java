package com.example.movieshare.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.movieshare.listeners.OnItemClickListener;
import com.example.movieshare.repository.models.MovieComment;

public class CommentViewHolder extends MovieItemViewHolder<MovieComment> {
    public CommentViewHolder(@NonNull View itemView, OnItemClickListener listener){
        super(itemView, listener);
    }

    @Override
    public void bindMovieItem(MovieComment movieComment) {
        this.movieItemNameTv.setText(movieComment.getMovieName());
        this.movieItemRatingTv.setText(movieComment.getMovieRating());
        this.movieItemDescriptionTv.setText(movieComment.getDescription());
    }
}
