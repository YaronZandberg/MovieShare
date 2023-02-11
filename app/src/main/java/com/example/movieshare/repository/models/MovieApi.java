package com.example.movieshare.repository.models;

import java.util.List;

public class MovieApi {
    Boolean adult;
    String backdrop_path;
    List<Integer> genre_ids;
    Integer id;
    String original_language;
    String original_title;
    String overview;
    Double popularity;
    String poster_path;
    String release_date;
    String title;
    Boolean video;
    Double vote_average;
    Integer vote_count;

    public Integer getId() {
        return id;
    }

    public List<Integer> getGenre_ids() {
        return genre_ids;
    }

    public String getTitle() {
        return title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public String getOverview() {
        return overview;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public String getPoster_path() {
        return poster_path;
    }
}
