package com.example.movieshare.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.movieshare.R;
import com.example.movieshare.listeners.general.OnItemClickListener;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.MovieComment;

public class UserCommentViewHolder extends MovieItemViewHolder<MovieComment> {
    public UserCommentViewHolder(@NonNull View itemView, OnItemClickListener listener){
        super(itemView, listener);
        this.movieItemNameTv = itemView.findViewById(R.id.item_list_row_user_comment_name_tv);
        this.movieItemRatingTv = itemView.findViewById(R.id.item_list_row_user_comment_rating_tv);
        this.movieItemDescriptionTv = itemView.findViewById(R.id.item_list_row_user_comment_comment_tv);
        this.movieItemImg = itemView.findViewById(R.id.item_list_row_user_comment_img);
    }

    @Override
    public void bindMovieItem(MovieComment movieComment) {
        String userId = movieComment.getUserId();
        Repository.getRepositoryInstance().getFirebaseModel().getUserExecutor()
                .getUserById(userId, user -> {
                    String userName = user.getFirstName().concat(" ").concat(user.getLastName());
                    this.movieItemNameTv.setText(userName);
                    this.movieItemRatingTv.setText(movieComment.getMovieRatingOfComment());
                    this.movieItemDescriptionTv.setText(movieComment.getDescription());
                });
    }
}
