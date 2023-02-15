package com.example.movieshare.viewmodels.user;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.MovieComment;

import java.util.List;

public class UserCommentEditionFragmentViewModel extends ViewModel {
    private final LiveData<List<MovieComment>> allMovieComments =
            Repository.getRepositoryInstance().getAllMovieComments();
    private List<MovieComment> allUserMovieComments;
    private MovieComment movieComment;
    private Integer movieCommentPositionInTotalList;

    public LiveData<List<MovieComment>> getAllMovieComments() {
        return this.allMovieComments;
    }

    public List<MovieComment> getAllUserMovieComments() {
        return this.allUserMovieComments;
    }

    public void setAllUserMovieComments(List<MovieComment> allUserMovieComments) {
        this.allUserMovieComments = allUserMovieComments;
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
