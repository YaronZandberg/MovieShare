package com.example.movieshare;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.example.movieshare.repository.Repository;

public class IntroActivity extends AppCompatActivity {

    private static final int TIME_OUT = 3000;

    View first,second,third,fourth,fifth,sixth;
    TextView a, slogan;
    Animation topAnimantion ,bottomAnimation ,middleAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_intro);
        setDataMembers();
        createAnimation();
        bindAnimation();

        new Handler().postDelayed(this::isLogin, TIME_OUT);
    }

    private void startGuestsActivity() {
        startActivityFromIntent(GuestsActivity.class);
    }

    private void startUsersActivity() {
        startActivityFromIntent(MainActivity.class);
    }

    private void startActivityFromIntent(Class<? extends AppCompatActivity> activityClass) {
        Intent intent = new Intent(this, activityClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.removeItem(R.id.userCommentAdditionFragment);
        menu.removeItem(R.id.userProfileFragment);
        menu.removeItem(R.id.logoutMenuItem);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private void setDataMembers() {
        first = findViewById(R.id.first_line);
        second = findViewById(R.id.second_line);
        third = findViewById(R.id.third_line);
        fourth = findViewById(R.id.fourth_line);
        fifth = findViewById(R.id.fifth_line);
        sixth = findViewById(R.id.sixth_line);
        a = findViewById(R.id.a);
        slogan = findViewById(R.id.tagLine);
    }

    private void createAnimation() {
        topAnimantion = AnimationUtils.loadAnimation(this, R.anim.top_animation);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_animation);
        middleAnimation = AnimationUtils.loadAnimation(this, R.anim.middle_animation);
    }

    private void bindAnimation() {
        first.setAnimation(topAnimantion);
        second.setAnimation(topAnimantion);
        third.setAnimation(topAnimantion);
        fourth.setAnimation(topAnimantion);
        fifth.setAnimation(topAnimantion);
        sixth.setAnimation(topAnimantion);
        a.setAnimation(middleAnimation);
        slogan.setAnimation(bottomAnimation);
    }

    private void isLogin() {
        Repository.getRepositoryInstance().getExecutor().execute(() -> {
            if (Repository.getRepositoryInstance().getAuthModel().isSignedIn()) {
                Repository.getRepositoryInstance().getMainThreadHandler().post((this::startUsersActivity));
            } else {
                Repository.getRepositoryInstance().getMainThreadHandler().post((this::startGuestsActivity));
            }
        });
    }
}