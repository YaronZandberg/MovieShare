package com.example.movieshare;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.movieshare.constants.Categories;
import com.example.movieshare.listeners.GetMovieItemListener;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.dao.MovieApiCaller;
import com.example.movieshare.repository.firebase.executors.MovieCategoryExecutor;
import com.example.movieshare.repository.firebase.executors.MovieExecutor;
import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.repository.models.MovieApi;
import com.example.movieshare.repository.models.MovieApiList;
import com.example.movieshare.repository.models.MovieCategory;

import java.util.List;
import java.util.function.Function;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.createCategories();
        setContentView(R.layout.activity_main);
        NavHostFragment navHostFragment =
                (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.main_navhost);
        this.navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, this.navController);
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