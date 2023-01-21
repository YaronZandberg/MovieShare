package com.example.movieshare.repository.handlers;

import com.example.movieshare.repository.models.MovieComment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class MovieCommentHandler {
    private final Integer MOVIE_COMMENTS_AMOUNT = 15;
    private final List<MovieComment> movieCommentList = new ArrayList();
    private static final MovieCommentHandler movieCommentHandlerInstance = new MovieCommentHandler();

    private MovieCommentHandler() {
        for (int i = 0; i < this.MOVIE_COMMENTS_AMOUNT; i++) {
            this.addMovieComment(initializeMovieComment(i));
        }
    }

    public static MovieCommentHandler instance() {
        return movieCommentHandlerInstance;
    }

    private MovieComment initializeMovieComment(Integer index) {
        Integer id = index;
        Integer userId = index;
        Integer movieId = index;
        String description = "description " + index;
        String movieName = "name " + index;
        String movieRating = "rating " + index;
        return new MovieComment(id, userId, movieId, description, movieName, movieRating);
    }

    public List<MovieComment> getAllMovieComments() {
        return movieCommentList;
    }

    public MovieComment getMovieCommentById(Integer id) {
        return movieCommentList.get(id);
    }

    public List<MovieComment> getAllMovieCommentsByUserId(Integer userId){
        return this.movieCommentList
                .stream()
                .filter(movieComment -> movieComment.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<MovieComment> getAllMovieCommentsByMovieId(Integer movieId){
        return this.movieCommentList
                .stream()
                .filter(movieComment -> movieComment.getMovieId().equals(movieId))
                .collect(Collectors.toList());
    }

    public void addMovieComment(MovieComment movieComment) {
        movieCommentList.add(movieComment);
    }

    public void setMovieComment(Integer index, MovieComment movieComment) {
        movieCommentList.set(index, movieComment);
    }

    public void removeMovieComment(Integer index) {
        movieCommentList.remove(movieCommentList.get(index));
    }
}
