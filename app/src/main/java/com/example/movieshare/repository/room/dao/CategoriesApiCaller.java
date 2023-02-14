package com.example.movieshare.repository.room.dao;

import com.example.movieshare.repository.models.CategoriesApi;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface CategoriesApiCaller {
    @Headers("Content-Type: application/json")
    @GET("/3/genre/movie/list")
    Call<CategoriesApi> getJson(@Query("api_key") String api_key);
}
