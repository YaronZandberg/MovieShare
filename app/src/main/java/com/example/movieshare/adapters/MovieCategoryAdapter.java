package com.example.movieshare.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshare.R;
import com.example.movieshare.listeners.OnItemClickListener;
import com.example.movieshare.repository.models.MovieCategory;
import com.example.movieshare.viewholders.MovieCategoryViewHolder;

import java.util.List;

public class MovieCategoryAdapter extends RecyclerView.Adapter<MovieCategoryViewHolder> {
    private final LayoutInflater layoutInflater;
    private List<MovieCategory> movieCategoryList;
    private OnItemClickListener listener;

    public MovieCategoryAdapter(LayoutInflater layoutInflater, List<MovieCategory> movieCommentList) {
        this.layoutInflater = layoutInflater;
        this.movieCategoryList = movieCommentList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public void setMovieCategoryList(List<MovieCategory> movieCategoryList) {
        this.movieCategoryList = movieCategoryList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = this.layoutInflater.inflate(R.layout.item_list_row, parent, false);
        return new MovieCategoryViewHolder(view, this.listener, this.movieCategoryList);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieCategoryViewHolder movieCategoryViewHolder, int position) {
        MovieCategory movieCategory = this.movieCategoryList.get(position);
        movieCategoryViewHolder.bindMovieCategory(movieCategory);
    }

    @Override
    public int getItemCount() {
        return this.movieCategoryList.size();
    }
}
