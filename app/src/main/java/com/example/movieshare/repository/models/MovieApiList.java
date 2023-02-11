package com.example.movieshare.repository.models;

import java.util.List;

public class MovieApiList {
    int page;
    List<MovieApi> results;

    public List<MovieApi> getResults() {
        return results;
    }
}
