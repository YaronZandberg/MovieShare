package com.example.movieshare.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.movieshare.R;
import com.example.movieshare.listeners.general.OnItemClickListener;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.models.User;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class MovieCommentViewHolder extends MovieItemViewHolder<MovieComment> {
    public MovieCommentViewHolder(@NonNull View itemView, OnItemClickListener listener){
        super(itemView, listener);
        this.movieItemNameTv = itemView.findViewById(R.id.item_list_row_movie_comment_name_tv);
        this.movieItemRatingTv = itemView.findViewById(R.id.item_list_row_movie_comment_rating_tv);
        this.movieItemDescriptionTv = itemView.findViewById(R.id.item_list_row_movie_comment_comment_tv);
        this.movieItemImg = itemView.findViewById(R.id.item_list_row_movie_comment_img);
    }

    @Override
    public void bindMovieItem(MovieComment movieComment) {
        this.movieItemNameTv.setText(movieComment.getMovieName());
        this.movieItemRatingTv.setText(movieComment.getMovieRatingOfComment());
        this.movieItemDescriptionTv.setText(movieComment.getDescription());
        getCurrentUser();
    }

    private void getCurrentUser() {
        String userId = Repository.getRepositoryInstance().getAuthModel().getCurrentUserUid();
        Repository.getRepositoryInstance().getFirebaseModel().getUserExecutor()
                .getUserById(userId, this::loadUserProfileImage);
    }

    private void loadUserProfileImage(User user) {
        if (Objects.nonNull(user.getImageUrl())) {
            Picasso.get().load(user.getImageUrl())
                    .placeholder(R.drawable.avatar)
                    .into(this.movieItemImg);
        } else {
            this.movieItemImg.setImageResource(R.drawable.avatar);
        }
    }
}
