package com.example.movieshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.MovieUtils;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Repository.getRepositoryInstance().getExecutor().execute(() -> {
            MovieUtils.simulateSleeping();

            if (Repository.getRepositoryInstance().getAuthModel().isSignedIn()) {
                Repository.getRepositoryInstance().getMainThreadHandler().post((this::startUsersActivity));
            } else {
                Repository.getRepositoryInstance().getMainThreadHandler().post((this::startGuestsActivity));
            }
        });
    }

    private void startGuestsActivity() {
        startActivityFromIntent(GuestsActivity.class);
    }

    private void startUsersActivity() {
        startActivityFromIntent(MainActivity.class);
    }

    private void startActivityFromIntent(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        startActivity(intent);
        finish();
    }
}