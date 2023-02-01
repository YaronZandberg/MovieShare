package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.CommentAdapter;
import com.example.movieshare.databinding.FragmentMovieCommentListBinding;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.Repository;

import java.util.ArrayList;
import java.util.List;

public class MovieCommentListFragment extends Fragment {
    private FragmentMovieCommentListBinding viewBindings;
    private List<MovieComment> movieCommentList = new ArrayList<>();
    private Integer movieId;
    private CommentAdapter movieCommentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieId = MovieCommentListFragmentArgs.fromBundle(getArguments()).getMovieId();
        reloadMovieCommentList();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieCommentListBinding.inflate(inflater, container, false);
        this.viewBindings.movieCommentListFragmentList.setHasFixedSize(true);
        this.viewBindings.movieCommentListFragmentList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieCommentAdapter = new CommentAdapter(getLayoutInflater(), this.movieCommentList);
        this.viewBindings.movieCommentListFragmentList.setAdapter(this.movieCommentAdapter);
        return this.viewBindings.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadMovieCommentList();
    }

    private void reloadMovieCommentList() {
        Repository.getMovieCommentHandler()
                .getAllMovieCommentsByMovieId(this.movieId, movieCommentList -> {
                    this.movieCommentList = movieCommentList;
                    this.movieCommentAdapter.setMovieItemList(this.movieCommentList);
                });
    }
}