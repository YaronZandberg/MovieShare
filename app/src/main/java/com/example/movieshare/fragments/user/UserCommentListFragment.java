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
import com.example.movieshare.fragments.base.MovieBaseFragment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.MovieUtils;
import com.example.movieshare.viewmodels.user.UserCommentListFragmentViewModel;

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
        this.viewBindings = FragmentUserCommentListBinding.inflate(inflater, container, false);
        this.viewBindings.userCommentListFragmentList.setHasFixedSize(true);
        this.viewBindings.userCommentListFragmentList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.movieCommentAdapter = new MovieCommentAdapter(getLayoutInflater(),
                this.viewModel.getUserCommentList());
        this.viewBindings.userCommentListFragmentList.setAdapter(this.movieCommentAdapter);
        this.configureMenuOptions(this.viewBindings.getRoot());
        activateItemListListener();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(UserCommentListFragmentViewModel.class);
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadUserCommentList();
    }

    private void reloadUserCommentList() {
        this.viewBindings.userCommentListFragmentProgressBar.setVisibility(View.VISIBLE);
        Repository.getRepositoryInstance().getLocalModel().getMovieCommentHandler()
                .getAllMovieCommentsByUserId(this.userId, movieCommentList -> {
                    this.viewModel.setUserCommentList(movieCommentList);
                    this.movieCommentAdapter.setMovieItemList(this.viewModel.getUserCommentList());
                    MovieUtils.simulateSleeping();
                    this.viewBindings.userCommentListFragmentProgressBar.setVisibility(View.GONE);
                });
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