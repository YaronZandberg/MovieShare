package com.example.movieshare.fragments.movie;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.MenuProvider;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.ViewModelProvider;
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
import com.example.movieshare.enums.LoadingState;
import com.example.movieshare.notifications.NotificationManager;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.viewmodels.movie.MovieCommentListFragmentViewModel;

import java.util.Objects;

public class MovieCommentListFragment extends Fragment {
    private FragmentMovieCommentListBinding viewBindings;
    private String movieId;
    private CommentAdapter movieCommentAdapter;
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
        this.movieCommentAdapter = new CommentAdapter(getLayoutInflater(),
                this.viewModel.getMovieCommentList().getValue());
        this.viewBindings.movieCommentListFragmentList.setAdapter(this.movieCommentAdapter);
        this.viewBindings.swipeRefresh.setOnRefreshListener(this::reloadMovieCommentList);
        configureMenuOptions();
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
                        this.movieCommentAdapter.setMovieItemList(movieCommentList));
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