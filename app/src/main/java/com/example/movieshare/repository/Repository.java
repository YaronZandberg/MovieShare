package com.example.movieshare.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Repository {
    private final Integer MOVIE_COMMENTS_AMOUNT = 20;
    private static final Repository repositoryInstance = new Repository();
    private final List<MovieComment> movieCommentList = new ArrayList();

    private Repository() {
        for (int i = 0; i < this.MOVIE_COMMENTS_AMOUNT; i++) {
            this.addMovieComment(initializeMovieComment(i));
        }
    }

    public static Repository instance() {
        return repositoryInstance;
    }

    private MovieComment initializeMovieComment(Integer index) {
        String id = String.valueOf(index);
        String userId = "user " + index;
        String movieId = "movie " + index;
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

    public List<MovieComment> getAllMovieCommentsByUserId(String userId){
        return this.movieCommentList
                .stream()
                .filter(movieComment -> movieComment.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<MovieComment> getAllMovieCommentsByMovieId(String movieId){
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
