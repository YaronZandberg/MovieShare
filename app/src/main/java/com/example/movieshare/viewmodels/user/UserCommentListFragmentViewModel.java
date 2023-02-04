package com.example.movieshare.viewmodels.user;

import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.models.MovieComment;

import java.util.ArrayList;
import java.util.List;

public class UserCommentListFragmentViewModel extends ViewModel {
    private List<MovieComment> userCommentList = new ArrayList<>();

    public List<MovieComment> getUserCommentList() {
        return this.userCommentList;
    }

    public void setUserCommentList(List<MovieComment> userCommentList) {
        this.userCommentList = userCommentList;
    }
}
