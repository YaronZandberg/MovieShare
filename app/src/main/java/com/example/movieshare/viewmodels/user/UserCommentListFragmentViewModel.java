package com.example.movieshare.viewmodels.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.MovieComment;

import java.util.List;

public class UserCommentListFragmentViewModel extends ViewModel {
    private final LiveData<List<MovieComment>> userCommentList =
            Repository.getRepositoryInstance().getAllMovieComments();

    public LiveData<List<MovieComment>> getUserCommentList() {
        return this.userCommentList;
    }
}
