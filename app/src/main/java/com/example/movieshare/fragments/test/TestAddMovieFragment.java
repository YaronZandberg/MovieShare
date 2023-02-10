package com.example.movieshare.fragments.test;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieshare.databinding.FragmentTestAddMovieBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.Movie;

public class TestAddMovieFragment extends Fragment {
    private FragmentTestAddMovieBinding viewBindings;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentTestAddMovieBinding.inflate(inflater, container, false);
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    private void activateButtonsListeners() {
        this.viewBindings.testMovieCancelBtn.setOnClickListener(view ->
                Navigation.findNavController(view).popBackStack());
        this.viewBindings.testMovieSaveBtn.setOnClickListener(view -> {
            Movie movie = buildNewMovie();
            Repository.getRepositoryInstance().getFirebaseModel().getMovieExecutor()
                    .addMovie(movie, () -> {
                                Toast.makeText(getContext(),
                                                "Add movie operation finished successfully",
                                                Toast.LENGTH_LONG)
                                        .show();
                                Repository.getRepositoryInstance().refreshAllMovies();
                            }
                    );
        });
    }

    private Movie buildNewMovie() {
        String categoryId = this.viewBindings.testMovieCategoryIdInputEt.getText().toString();
        String movieName = this.viewBindings.testMovieNameInputEt.getText().toString();
        String movieRating = this.viewBindings.testMovieRatingInputEt.getText().toString();
        String description = this.viewBindings.testMovieDesctiptionInputEt.getText().toString();
        return new Movie(categoryId, movieName, movieRating, description);
    }
}