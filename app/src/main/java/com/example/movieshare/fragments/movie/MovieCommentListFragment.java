package com.example.movieshare.fragments.movie;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.UserCommentAdapter;
import com.example.movieshare.databinding.FragmentMovieCommentListBinding;
import com.example.movieshare.enums.LoadingState;
import com.example.movieshare.fragments.base.MovieBaseFragment;
import com.example.movieshare.notifications.NotificationManager;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.viewmodels.movie.MovieCommentListFragmentViewModel;

public class MovieCommentListFragment extends MovieBaseFragment {
    private FragmentMovieCommentListBinding viewBindings;
    private String movieId;
    private UserCommentAdapter userCommentAdapter;
    private MovieCommentListFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieId = MovieCommentListFragmentArgs.fromBundle(getArguments()).getMovieId();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeAllMovieComments();
        this.viewBindings = FragmentMovieCommentListBinding.inflate(inflater, container, false);
        this.viewBindings.movieCommentListFragmentList.setHasFixedSize(true);
        this.viewBindings.movieCommentListFragmentList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.userCommentAdapter = new UserCommentAdapter(getLayoutInflater(),
                this.viewModel.getMovieCommentList().getValue());
        this.viewBindings.movieCommentListFragmentList.setAdapter(this.userCommentAdapter);
        this.viewBindings.swipeRefresh.setOnRefreshListener(this::initializeAllMovieComments);
        this.configureMenuOptions(this.viewBindings.getRoot());
        activateItemListListener();
        this.viewModel.getMovieCommentList()
                .observe(getViewLifecycleOwner(), movieComments -> reloadMovieCommentList());
        NotificationManager.instance()
                .getEventMovieCommentListLoadingState()
                .observe(getViewLifecycleOwner(),
                        loadingState -> this.viewBindings.swipeRefresh
                                .setRefreshing(loadingState == LoadingState.LOADING));
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(MovieCommentListFragmentViewModel.class);
    }

    private void initializeAllMovieComments() {
        Repository.getRepositoryInstance().refreshAllMovieComments();
    }

    private void reloadMovieCommentList() {
        Repository.getRepositoryInstance().getLocalModel().getMovieCommentHandler()
                .getAllMovieCommentsByMovieId(this.movieId, movieCommentList ->
                        this.userCommentAdapter.setMovieItemList(movieCommentList));
    }

    private void activateItemListListener() {
        this.userCommentAdapter.setOnItemClickListener(position -> {});
    }
}