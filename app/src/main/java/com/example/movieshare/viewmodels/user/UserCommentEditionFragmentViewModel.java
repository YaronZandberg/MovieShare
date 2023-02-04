package com.example.movieshare.viewmodels.user;

import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.models.MovieComment;

import java.util.List;

public class UserCommentEditionFragmentViewModel extends ViewModel {
    private List<MovieComment> allUserMovieComments;
    private MovieComment movieComment;
    private List<MovieComment> allMovieComments;
    private Integer movieCommentPositionInTotalList;

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

    public List<MovieComment> getAllMovieComments() {
        return this.allMovieComments;
    }

    public void setAllMovieComments(List<MovieComment> allMovieComments) {
        this.allMovieComments = allMovieComments;
    }

    public Integer getMovieCommentPositionInTotalList() {
        return this.movieCommentPositionInTotalList;
    }

    public void setMovieCommentPositionInTotalList(Integer movieCommentPositionInTotalList) {
        this.movieCommentPositionInTotalList = movieCommentPositionInTotalList;
    }
}
