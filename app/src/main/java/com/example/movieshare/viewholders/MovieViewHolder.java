package com.example.movieshare.viewholders;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshare.R;
import com.example.movieshare.listeners.OnItemClickListener;
import com.example.movieshare.repository.models.Movie;

import java.util.List;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private final List<Movie> movieList;
    private final TextView movieNameTv;
    private final TextView movieRatingTv;
    private final TextView movieDescriptionTv;

    public MovieViewHolder(@NonNull View itemView,
                           OnItemClickListener listener,
                           List<Movie> movieList) {
        super(itemView);
        this.movieList = movieList;
        this.movieNameTv = itemView.findViewById(R.id.item_list_row_name_tv);
        this.movieRatingTv = itemView.findViewById(R.id.item_list_row_rating_tv);
        this.movieDescriptionTv = itemView.findViewById(R.id.item_list_row_comment_tv);
        itemView.setOnClickListener(view -> {
            Integer position = getAdapterPosition();
            Log.d("TAG", "row clicked " + position);
            listener.onItemClick(position);
        });
    }

    public void bindMovie(Movie movie) {
        this.movieNameTv.setText(movie.getMovieName());
        this.movieRatingTv.setText(movie.getMovieRating());
        this.movieDescriptionTv.setText(movie.getDescription());
    }
}
