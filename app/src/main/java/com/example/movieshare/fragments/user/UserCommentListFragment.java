package com.example.movieshare.fragments.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.CommentAdapter;
import com.example.movieshare.databinding.FragmentUserCommentListBinding;
import com.example.movieshare.repository.models.MovieComment;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.MovieUtils;

import java.util.ArrayList;
import java.util.List;

public class UserCommentListFragment extends Fragment {
    private FragmentUserCommentListBinding viewBindings;
    private List<MovieComment> userCommentList = new ArrayList<>();
    private Integer userId;
    private CommentAdapter userCommentAdapter;

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
        this.userCommentAdapter = new CommentAdapter(getLayoutInflater(), this.userCommentList);
        this.viewBindings.userCommentListFragmentList.setAdapter(this.userCommentAdapter);
        activateItemListListener();
        activateButtonsListeners();
        return this.viewBindings.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadUserCommentList();
    }

    private void reloadUserCommentList() {
        this.viewBindings.userCommentListFragmentProgressBar.setVisibility(View.VISIBLE);
        Repository.getMovieCommentHandler()
                .getAllMovieCommentsByUserId(this.userId, movieCommentList -> {
                    this.userCommentList = movieCommentList;
                    this.userCommentAdapter.setMovieItemList(this.userCommentList);
                    MovieUtils.simulateSleeping();
                    this.viewBindings.userCommentListFragmentProgressBar.setVisibility(View.GONE);
                });
    }

    private void activateItemListListener() {
        this.userCommentAdapter.setOnItemClickListener(position -> {
            UserCommentListFragmentDirections
                    .ActionUserCommentListFragmentToUserCommentEditionFragment action =
                    UserCommentListFragmentDirections
                            .actionUserCommentListFragmentToUserCommentEditionFragment(position, this.userId);
            Navigation.findNavController(viewBindings.userCommentListFragmentList).navigate(action);
        });
    }

    private void activateButtonsListeners() {
        this.viewBindings.userCommentListFragmentAddBtn.setOnClickListener(view -> {
            NavDirections action = UserCommentListFragmentDirections
                    .actionUserCommentListFragmentToUserCommentAdditionFragment();
            Navigation.findNavController(view).navigate(action);
        });
    }
}