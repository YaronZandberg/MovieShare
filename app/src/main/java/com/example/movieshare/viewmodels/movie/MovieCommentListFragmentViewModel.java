package com.example.movieshare.viewmodels.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.MovieComment;

import java.util.List;

public class MovieCommentListFragmentViewModel extends ViewModel {
    private final LiveData<List<MovieComment>> movieCommentList =
            Repository.getRepositoryInstance().getAllMovieComments();

    public LiveData<List<MovieComment>> getMovieCommentList() {
        return this.movieCommentList;
    }
}
