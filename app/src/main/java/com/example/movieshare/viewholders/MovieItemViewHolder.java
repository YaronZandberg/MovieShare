package com.example.movieshare.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshare.listeners.general.OnItemClickListener;

public abstract class MovieItemViewHolder <T> extends RecyclerView.ViewHolder {
    protected TextView movieItemNameTv;
    protected TextView movieItemRatingTv;
    protected TextView movieItemDescriptionTv;
    protected ImageView movieItemImg;

    public MovieItemViewHolder(@NonNull View itemView, OnItemClickListener listener) {
        super(itemView);
        itemView.setOnClickListener(view -> {
            Integer position = getAdapterPosition();
            Log.d("TAG", "row clicked " + position);
            listener.onItemClick(position);
        });
    }

    public abstract void bindMovieItem(T movieItem);
}
