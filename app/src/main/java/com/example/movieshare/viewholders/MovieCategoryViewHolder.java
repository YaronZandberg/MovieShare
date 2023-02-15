package com.example.movieshare.viewholders;

import android.view.View;

import androidx.annotation.NonNull;

import com.example.movieshare.R;
import com.example.movieshare.listeners.general.OnItemClickListener;
import com.example.movieshare.repository.models.MovieCategory;

public class MovieCategoryViewHolder extends MovieItemViewHolder<MovieCategory> {
    public MovieCategoryViewHolder(@NonNull View itemView, OnItemClickListener listener){
        super(itemView, listener);
        this.movieItemNameTv = itemView.findViewById(R.id.item_list_row_category_name_tv);
        this.movieItemImg = itemView.findViewById(R.id.item_list_row_category_img);
    }

    @Override
    public void bindMovieItem(MovieCategory movieCategory) {
        this.movieItemNameTv.setText(movieCategory.getCategoryName());
        this.movieItemImg.setImageResource(R.drawable.logo);
    }
}
