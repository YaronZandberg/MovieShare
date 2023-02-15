package com.example.movieshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.movieshare.R;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.viewholders.MovieCommentViewHolder;
import com.example.movieshare.viewholders.MovieItemViewHolder;

import java.util.List;

public class MovieCommentAdapter extends MovieItemAdapter<MovieComment> {
    public MovieCommentAdapter(LayoutInflater layoutInflater, List<MovieComment> movieItemList){
        super(layoutInflater, movieItemList);
    }

    @NonNull
    @Override
    public MovieCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_list_row_movie_comment, parent, false);
        return new MovieCommentViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder commentViewHolder, int position) {
        MovieComment movieComment = this.movieItemList.get(position);
        commentViewHolder.bindMovieItem(movieComment);
    }
}
