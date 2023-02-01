package com.example.movieshare.fragments.test;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.movieshare.databinding.FragmentTestAddMovieCategoryBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.models.MovieCategory;

public class TestAddMovieCategoryFragment extends Fragment {
    private FragmentTestAddMovieCategoryBinding viewBindings;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentTestAddMovieCategoryBinding
                .inflate(inflater, container, false);
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    private void activateButtonsListeners() {
        this.viewBindings.testCategoryCancelBtn.setOnClickListener(view ->
                Navigation.findNavController(view).popBackStack());
        this.viewBindings.testCategorySaveBtn.setOnClickListener(view -> {
            MovieCategory movieCategory = buildNewMovieCategory();
            Repository.getMovieCategoryHandler()
                    .addMovieCategory(movieCategory, () -> Toast.makeText(getContext(),
                                    "Add movie category operation finished successfully",
                                    Toast.LENGTH_LONG)
                            .show()
                    );
        });
    }

    private MovieCategory buildNewMovieCategory() {
        String categoryName = this.viewBindings.testCategoryNameInputEt.getText().toString();
        String categoryRating = this.viewBindings.testCategoryRatingInputEt.getText().toString();
        String description = this.viewBindings.testCategoryDescriptionInputEt.getText().toString();
        return new MovieCategory(categoryName, categoryRating, description);
    }
}