package com.example.movieshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshare.R;
import com.example.movieshare.monitoring.OnItemClickListener;
import com.example.movieshare.repository.MovieComment;
import com.example.movieshare.viewholders.CommentViewHolder;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentViewHolder> {
    private final LayoutInflater layoutInflater;
    private final List<MovieComment> movieCommentList;
    private OnItemClickListener listener;

    public CommentAdapter(LayoutInflater layoutInflater, List<MovieComment> movieCommentList) {
        this.layoutInflater = layoutInflater;
        this.movieCommentList = movieCommentList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.comment_list_row, parent, false);
        return new CommentViewHolder(view, this.listener, this.movieCommentList);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder userCommentViewHolder, int position) {
        MovieComment movieComment = this.movieCommentList.get(position);
        userCommentViewHolder.bindMovieComment(movieComment);
    }

    @Override
    public int getItemCount() {
        return this.movieCommentList.size();
    }
}
