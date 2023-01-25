package com.example.movieshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshare.R;
import com.example.movieshare.listeners.OnItemClickListener;
import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.viewholders.MovieViewHolder;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<Movie> movieList;
    private OnItemClickListener listener;

    public MovieAdapter(LayoutInflater layoutInflater, List<Movie> movieList) {
        this.layoutInflater = layoutInflater;
        this.movieList = movieList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_list_row, parent, false);
        return new MovieViewHolder(view, this.listener, this.movieList);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder movieViewHolder, int position) {
        Movie movie = this.movieList.get(position);
        movieViewHolder.bindMovie(movie);
    }

    @Override
    public int getItemCount() {
        return this.movieList.size();
    }
}
