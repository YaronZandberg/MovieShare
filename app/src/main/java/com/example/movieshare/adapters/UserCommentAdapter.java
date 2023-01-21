package com.example.movieshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshare.R;
import com.example.movieshare.monitoring.OnItemClickListener;
import com.example.movieshare.repository.MovieComment;
import com.example.movieshare.viewholders.UserCommentViewHolder;

import java.util.List;

public class UserCommentAdapter extends RecyclerView.Adapter<UserCommentViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<MovieComment> movieCommentList;
    private OnItemClickListener listener;

    public UserCommentAdapter(LayoutInflater layoutInflater, List<MovieComment> movieCommentList) {
        this.layoutInflater = layoutInflater;
        this.movieCommentList = movieCommentList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    @NonNull
    @Override
    public UserCommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.user_comment_list_row, parent, false);
        return new UserCommentViewHolder(view, this.listener, this.movieCommentList);
    }

    @Override
    public void onBindViewHolder(@NonNull UserCommentViewHolder userCommentViewHolder, int position) {
        MovieComment movieComment = this.movieCommentList.get(position);
        userCommentViewHolder.bindMovieComment(movieComment);
    }

    @Override
    public int getItemCount() {
        return this.movieCommentList.size();
    }
}
