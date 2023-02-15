package com.example.movieshare.fragments.user;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.MovieCommentAdapter;
import com.example.movieshare.databinding.FragmentUserCommentListBinding;
import com.example.movieshare.enums.LoadingState;
import com.example.movieshare.fragments.base.MovieBaseFragment;
import com.example.movieshare.notifications.NotificationManager;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.viewmodels.user.UserCommentListFragmentViewModel;

import java.util.Objects;

public class UserCommentListFragment extends MovieBaseFragment {
    private FragmentUserCommentListBinding viewBindings;
    private String userId;
    private MovieCommentAdapter movieCommentAdapter;
    private UserCommentListFragmentViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userId = UserCommentListFragmentArgs.fromBundle(getArguments()).getUserId();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeUserComments();
        this.viewBindings = FragmentUserCommentListBinding.inflate(inflater, container, false);
        this.viewBindings.userCommentListFragmentList.setHasFixedSize(true);
        this.viewBindings.userCommentListFragmentList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieCommentAdapter = new MovieCommentAdapter(getLayoutInflater(),
                this.viewModel.getUserCommentList().getValue());
        this.viewBindings.userCommentListFragmentList.setAdapter(this.movieCommentAdapter);
        this.viewBindings.swipeRefresh.setOnRefreshListener(this::initializeUserComments);
        this.configureMenuOptions(this.viewBindings.getRoot());
        this.viewModel.getUserCommentList().observe(getViewLifecycleOwner(),
                movieCommentList -> reloadUserCommentList());
        NotificationManager.instance()
                .getEventUserCommentListLoadingState()
                .observe(getViewLifecycleOwner(),
                        loadingState -> this.viewBindings.swipeRefresh
                                .setRefreshing(loadingState == LoadingState.LOADING));
        activateItemListListener();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(UserCommentListFragmentViewModel.class);
    }

    private void initializeUserComments() {
        Repository.getRepositoryInstance().refreshAllMovieComments();
    }

    private void reloadUserCommentList() {
        if (Objects.nonNull(this.viewModel.getUserCommentList().getValue())) {
            Repository.getRepositoryInstance().getLocalModel().getMovieCommentHandler()
                    .getAllMovieCommentsByUserId(this.userId, movieItemList ->
                            this.movieCommentAdapter.setMovieItemList(movieItemList));
        }
    }

    private void activateItemListListener() {
        this.movieCommentAdapter.setOnItemClickListener(position -> {
            UserCommentListFragmentDirections
                    .ActionUserCommentListFragmentToUserCommentEditionFragment action =
                    UserCommentListFragmentDirections
                            .actionUserCommentListFragmentToUserCommentEditionFragment(position, this.userId);
            Navigation.findNavController(viewBindings.userCommentListFragmentList).navigate(action);
        });
    }
}