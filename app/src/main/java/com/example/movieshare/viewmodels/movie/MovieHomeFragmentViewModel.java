package com.example.movieshare.viewmodels.movie;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.MovieCategory;

import java.util.List;

public class MovieHomeFragmentViewModel extends ViewModel {
    private final LiveData<List<MovieCategory>> movieCategories =
            Repository.getRepositoryInstance().getAllMovieCategories();

    public LiveData<List<MovieCategory>> getMovieCategories() {
        return this.movieCategories;
    }
}
