package com.example.movieshare;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.dao.MovieApiCaller;
import com.example.movieshare.repository.models.MovieApiList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.themoviedb.org").addConverterFactory(GsonConverterFactory.create()).build();

        MovieApiCaller service = retrofit.create(MovieApiCaller.class);


        service.getJson("fd03ee4400dc93124b178d4ffbf867d1", 18).enqueue((new Callback<MovieApiList>() {
            @Override
            public void onResponse(Call<MovieApiList> call, Response<MovieApiList> response) {
                response.body().getResults();
                Repository.
            }

            @Override
            public void onFailure(Call<MovieApiList> call, Throwable t) {
                Log.d("test", t.toString());
            }
        }));
        super.onCreate(savedInstanceState);
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
}