package com.example.movieshare.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshare.R;
import com.example.movieshare.listeners.OnItemClickListener;

public abstract class MovieItemViewHolder <T> extends RecyclerView.ViewHolder {
    protected final TextView movieItemNameTv;
    protected final TextView movieItemRatingTv;
    protected final TextView movieItemDescriptionTv;

    protected final ImageView movieItemNameImg;

    public MovieItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        this.movieItemNameImg = itemView.findViewById(R.id.item_list_row_img);
        this.movieItemNameTv = itemView.findViewById(R.id.item_list_row_name_tv);
        this.movieItemRatingTv = itemView.findViewById(R.id.item_list_row_rating_tv);
        this.movieItemDescriptionTv = itemView.findViewById(R.id.item_list_row_comment_tv);
        itemView.setOnClickListener(view -> {
            Integer position = getAdapterPosition();
            Log.d("TAG", "row clicked " + position);
            listener.onItemClick(position);
        });
    }

    public abstract void bindMovieItem(T movieItem);
}
