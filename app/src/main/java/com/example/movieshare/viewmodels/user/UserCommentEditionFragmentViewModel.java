package com.example.movieshare.viewmodels.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.MovieComment;

import java.util.List;

public class UserCommentEditionFragmentViewModel extends ViewModel {
    private final LiveData<List<MovieComment>> allMovieComments =
            Repository.getRepositoryInstance().getAllMovieComments();
    private List<MovieComment> userMovieComments;
    private MovieComment movieComment;
    private Integer movieCommentPositionInTotalList;

    public LiveData<List<MovieComment>> getAllMovieComments() {
        return this.allMovieComments;
    }

    public List<MovieComment> getUserMovieComments() {
        return this.userMovieComments;
    }

    public void setUserMovieComments(List<MovieComment> userMovieComments) {
        this.userMovieComments = userMovieComments;
    }

    public MovieComment getMovieComment() {
        return this.movieComment;
    }

    public void setMovieComment(MovieComment movieComment) {
        this.movieComment = movieComment;
    }

    public Integer getMovieCommentPositionInTotalList() {
        return this.movieCommentPositionInTotalList;
    }

    public void setMovieCommentPositionInTotalList(Integer movieCommentPositionInTotalList) {
        this.movieCommentPositionInTotalList = movieCommentPositionInTotalList;
    }
}
