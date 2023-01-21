package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.CommentAdapter;
import com.example.movieshare.databinding.FragmentMovieCommentListBinding;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.Repository;

import java.util.List;

public class MovieCommentListFragment extends Fragment {
    private FragmentMovieCommentListBinding viewBindings;
    private List<MovieComment> movieCommentList;
    private String movieId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieCommentListBinding.inflate(inflater, container, false);

        // TODO: Initialize movieCommentList using Repository.instance.getAllMovieCommentsByMovieId()
        this.movieCommentList = Repository.getMovieCommentHandler().getAllMovieComments();

        RecyclerView movieCommentsRecyclerList = this.viewBindings.movieCommentListFragmentList;
        movieCommentsRecyclerList.setHasFixedSize(true);
        movieCommentsRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        CommentAdapter movieCommentAdapter = new CommentAdapter(getLayoutInflater(), this.movieCommentList);
        movieCommentsRecyclerList.setAdapter(movieCommentAdapter);

        return this.viewBindings.getRoot();
    }
}