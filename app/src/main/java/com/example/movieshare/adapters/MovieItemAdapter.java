package com.example.movieshare.adapters;

import android.view.LayoutInflater;

import androidx.recyclerview.widget.RecyclerView;

import com.example.movieshare.listeners.OnItemClickListener;
import com.example.movieshare.viewholders.MovieItemViewHolder;

import java.util.List;

public abstract class MovieItemAdapter <T> extends RecyclerView.Adapter<MovieItemViewHolder>{
    protected final LayoutInflater layoutInflater;
    protected List<T> movieItemList;
    protected OnItemClickListener listener;

    public MovieItemAdapter(LayoutInflater layoutInflater, List<T> movieItemList) {
        this.layoutInflater = layoutInflater;
        this.movieItemList = movieItemList;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.listener = onItemClickListener;
    }

    public void setMovieItemList(List<T> movieItemList) {
        this.movieItemList = movieItemList;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return this.movieItemList.size();
    }
}
