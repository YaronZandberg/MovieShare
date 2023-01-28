package com.example.movieshare.fragments.user;

import android.os.Bundle;

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

import java.util.List;

public class UserCommentListFragment extends Fragment {
    private FragmentUserCommentListBinding viewBindings;
    private List<MovieComment> userCommentList;
    private Integer userId;
    private CommentAdapter userCommentAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.userId = UserCommentListFragmentArgs.fromBundle(getArguments()).getUserId();
        reloadList();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings =
                FragmentUserCommentListBinding.inflate(inflater, container, false);
        this.viewBindings.userCommentListFragmentList.setHasFixedSize(true);
        this.viewBindings.userCommentListFragmentList.setLayoutManager(new LinearLayoutManager(getContext()));
        this.userCommentAdapter = new CommentAdapter(getLayoutInflater(), this.userCommentList);
        this.viewBindings.userCommentListFragmentList.setAdapter(this.userCommentAdapter);

        this.userCommentAdapter.setOnItemClickListener(position -> {
            UserCommentListFragmentDirections
                    .ActionUserCommentListFragmentToUserCommentEditionFragment action =
                    UserCommentListFragmentDirections
                            .actionUserCommentListFragmentToUserCommentEditionFragment(position);
            Navigation.findNavController(viewBindings.userCommentListFragmentList).navigate(action);
        });

        this.viewBindings.userCommentListFragmentAddBtn.setOnClickListener(view -> {
            NavDirections action = UserCommentListFragmentDirections
                    .actionUserCommentListFragmentToUserCommentAdditionFragment();
            Navigation.findNavController(view).navigate(action);
        });

        return this.viewBindings.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadList();
    }

    private void reloadList() {
        Repository.getMovieCommentHandler()
                .getAllMovieCommentsByUserId(this.userId, movieCommentList -> {
                    this.userCommentList = movieCommentList;
                    this.userCommentAdapter.setMovieCommentList(this.userCommentList);
                });
    }
}