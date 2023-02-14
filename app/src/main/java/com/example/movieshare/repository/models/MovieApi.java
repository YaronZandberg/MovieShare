package com.example.movieshare.repository.models;

import java.util.List;

public class MovieApi {
    Integer id;
    String original_title;
    String poster_path;
    String overview;
    Double vote_average;
    Boolean adult;
    String backdrop_path;
    List<Integer> genre_ids;
    String original_language;
    Double popularity;
    String release_date;
    String title;
    Boolean video;
    Integer vote_count;

    public Integer getId() {
        return id;
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
