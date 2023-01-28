package com.example.movieshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.movieshare.R;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.viewholders.CommentViewHolder;
import com.example.movieshare.viewholders.MovieItemViewHolder;

import java.util.List;

public class CommentAdapter extends MovieItemAdapter<MovieComment> {
    public CommentAdapter(LayoutInflater layoutInflater, List<MovieComment> movieItemList){
        super(layoutInflater, movieItemList);
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_list_row, parent, false);
        return new CommentViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder commentViewHolder, int position) {
        MovieComment movieComment = this.movieItemList.get(position);
        commentViewHolder.bindMovieItem(movieComment);
    }
}
