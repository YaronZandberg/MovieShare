package com.example.movieshare.viewmodels.movie;

import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.models.MovieComment;

import java.util.ArrayList;
import java.util.List;

public class MovieCommentListFragmentViewModel extends ViewModel {
    private List<MovieComment> movieCommentList = new ArrayList<>();

    public List<MovieComment> getMovieCommentList() {
        return this.movieCommentList;
    }

    public void setMovieCommentList(List<MovieComment> movieCommentList) {
        this.movieCommentList = movieCommentList;
    }
}
