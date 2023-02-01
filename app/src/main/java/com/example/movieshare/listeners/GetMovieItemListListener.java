package com.example.movieshare.listeners;

import java.util.List;

public interface GetMovieItemListListener <T> {
    void onComplete(List<T> movieItemList);
}
