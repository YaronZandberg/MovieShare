package com.example.movieshare.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshare.R;
import com.example.movieshare.monitoring.OnItemClickListener;
import com.example.movieshare.repository.models.MovieComment;

import java.util.List;

public class CommentViewHolder extends RecyclerView.ViewHolder {
    private final List<MovieComment> movieCommentList;
    private final TextView movieNameTv;
    private final TextView movieRatingTv;
    private final TextView movieCommentTv;

    public CommentViewHolder(@NonNull View itemView,
                             OnItemClickListener listener,
                             List<MovieComment> movieCommentList) {
        super(itemView);
        this.movieCommentList = movieCommentList;
        this.movieNameTv = itemView.findViewById(R.id.item_list_row_name_tv);
        this.movieRatingTv = itemView.findViewById(R.id.item_list_row_rating_tv);
        this.movieCommentTv = itemView.findViewById(R.id.item_list_row_comment_tv);
        itemView.setOnClickListener(view -> {
            Integer position = getAdapterPosition();
            Log.d("TAG", "row clicked " + position);
            listener.onItemClick(position);
        });
    }

    public void bindMovieComment(MovieComment movieComment) {
        this.movieNameTv.setText(movieComment.getMovieName());
        this.movieRatingTv.setText(movieComment.getMovieRating());
        this.movieCommentTv.setText(movieComment.getDescription());
    }
}
