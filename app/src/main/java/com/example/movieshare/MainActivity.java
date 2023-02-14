package com.example.movieshare;

import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.movieshare.constants.Categories;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.firebase.executors.MovieCategoryExecutor;
import com.example.movieshare.repository.models.MovieCategory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.createCategories();
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void createCategories() {
        MovieCategoryExecutor executor = Repository.getRepositoryInstance().getFirebaseModel().getMovieCategoryExecutor();
        Categories cat = new Categories();

        for(String CatId : cat.getCategories().keySet()) {
            String categoryName = cat.getCategoryById(CatId);

            executor.getMovieCategoryByName(categoryName, movieCategory -> {
                if(movieCategory == null) {
                    executor.addMovieCategory(new MovieCategory(categoryName, "0", categoryName), () -> {});
                }
            });
        }
    }
}