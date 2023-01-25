package com.example.movieshare.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshare.R;
import com.example.movieshare.listeners.OnItemClickListener;
import com.example.movieshare.repository.models.MovieCategory;

import java.util.List;

public class MovieCategoryViewHolder extends RecyclerView.ViewHolder {
    private final List<MovieCategory> movieCategoryList;
    private final TextView movieCategoryNameTv;
    private final TextView movieCategoryRatingTv;
    private final TextView movieCategoryDescriptionTv;

    public MovieCategoryViewHolder(@NonNull View itemView,
                                   OnItemClickListener listener,
                                   List<MovieCategory> movieCategoryList) {
        super(itemView);
        this.movieCategoryList = movieCategoryList;
        this.movieCategoryNameTv = itemView.findViewById(R.id.item_list_row_name_tv);
        this.movieCategoryRatingTv = itemView.findViewById(R.id.item_list_row_rating_tv);
        this.movieCategoryDescriptionTv = itemView.findViewById(R.id.item_list_row_comment_tv);
        itemView.setOnClickListener(view -> {
            Integer position = getAdapterPosition();
            Log.d("TAG", "row clicked " + position);
            listener.onItemClick(position);
        });
    }

    public void bindMovieCategory(MovieCategory movieCategory) {
        this.movieCategoryNameTv.setText(movieCategory.getCategoryName());
        this.movieCategoryRatingTv.setText(movieCategory.getCategoryRating());
        this.movieCategoryDescriptionTv.setText(movieCategory.getDescription());
    }
}
