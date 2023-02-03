package com.example.movieshare.fragments.movie;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.R;
import com.example.movieshare.adapters.CommentAdapter;
import com.example.movieshare.databinding.FragmentMovieCommentListBinding;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MovieCommentListFragment extends Fragment {
    private FragmentMovieCommentListBinding viewBindings;
    private List<MovieComment> movieCommentList = new ArrayList<>();
    private Integer movieId;
    private CommentAdapter movieCommentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieId = MovieCommentListFragmentArgs.fromBundle(getArguments()).getMovieId();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentMovieCommentListBinding.inflate(inflater, container, false);
        this.viewBindings.movieCommentListFragmentList.setHasFixedSize(true);
        this.viewBindings.movieCommentListFragmentList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieCommentAdapter = new CommentAdapter(getLayoutInflater(), this.movieCommentList);
        this.viewBindings.movieCommentListFragmentList.setAdapter(this.movieCommentAdapter);
        configureMenuOptions();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadMovieCommentList();
    }

    private void reloadMovieCommentList() {
        this.viewBindings.movieCommentListFragmentProgressBar.setVisibility(View.VISIBLE);
        Repository.getMovieCommentHandler()
                .getAllMovieCommentsByMovieId(this.movieId, movieCommentList -> {
                    this.movieCommentList = movieCommentList;
                    this.movieCommentAdapter.setMovieItemList(this.movieCommentList);
                    MovieUtils.simulateSleeping();
                    this.viewBindings.movieCommentListFragmentProgressBar.setVisibility(View.GONE);
                });
    }

    private void configureMenuOptions() {
        FragmentActivity parentActivity = getActivity();
        parentActivity.addMenuProvider(new MenuProvider() {
            @Override
            public void onCreateMenu(@NonNull Menu menu, @NonNull MenuInflater menuInflater) {
                menu.removeItem(R.id.userCommentAdditionFragment);
            }

            @Override
            public boolean onMenuItemSelected(@NonNull MenuItem menuItem) {
                if (menuItem.getItemId() == android.R.id.home) {
                    Navigation.findNavController(viewBindings.getRoot()).popBackStack();
                    return true;
                } else {
                    if (Objects.nonNull(viewBindings)) {
                        NavDirections action = MovieCommentListFragmentDirections.actionGlobalUserProfileFragment();
                        Navigation.findNavController(viewBindings.getRoot()).navigate(action);
                        return true;
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}