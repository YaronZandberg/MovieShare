package com.example.movieshare.repository.dao;

import androidx.room.Dao;

import com.example.movieshare.repository.models.MovieApiList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

@Dao
public interface MovieApiCaller {
    @Headers("Content-Type: application/json")
    @GET("/3/discover/movie")
    Call<MovieApiList> getJson(@Query ("api_key") String api_key, @Query("with_genres") int with_genres);
}