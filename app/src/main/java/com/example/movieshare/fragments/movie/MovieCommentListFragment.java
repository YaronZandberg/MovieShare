package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.CommentAdapter;
import com.example.movieshare.databinding.FragmentMovieCommentListBinding;
import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.Repository;

import java.util.List;

public class MovieCommentListFragment extends Fragment {
    private FragmentMovieCommentListBinding viewBindings;
    private List<MovieComment> movieCommentList;
    private Integer moviePosition;
    private Movie movie;
    private CommentAdapter movieCommentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.moviePosition = MovieCommentListFragmentArgs
                .fromBundle(getArguments()).getMoviePosition();
        this.movie = Repository.getMovieHandler().getAllMovies().get(this.moviePosition);
        Repository.getMovieCommentHandler()
                .getAllMovieCommentsByMovieId(this.movie.getMovieId(), movieCommentList -> {
                    this.movieCommentList = movieCommentList;
                    this.movieCommentAdapter.setMovieCommentList(this.movieCommentList);
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings =
                FragmentMovieCommentListBinding.inflate(inflater, container, false);
        this.viewBindings.movieCommentListFragmentList.setHasFixedSize(true);
        this.viewBindings.movieCommentListFragmentList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieCommentAdapter = new CommentAdapter(getLayoutInflater(), this.movieCommentList);
        this.viewBindings.movieCommentListFragmentList.setAdapter(this.movieCommentAdapter);
        return this.viewBindings.getRoot();
    }
}