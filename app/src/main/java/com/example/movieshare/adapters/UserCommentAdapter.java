package com.example.movieshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.movieshare.R;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.viewholders.MovieItemViewHolder;
import com.example.movieshare.viewholders.UserCommentViewHolder;

import java.util.List;

public class UserCommentAdapter extends MovieItemAdapter<MovieComment>{
    public UserCommentAdapter(LayoutInflater layoutInflater, List<MovieComment> movieItemList){
        super(layoutInflater, movieItemList);
    }

    @NonNull
    @Override
    public UserCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_list_row_user_comment, parent, false);
        return new UserCommentViewHolder(view, this.listener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieItemViewHolder userCommentViewHolder, int position) {
        MovieComment movieComment = this.movieItemList.get(position);
        userCommentViewHolder.bindMovieItem(movieComment);
    }
}
