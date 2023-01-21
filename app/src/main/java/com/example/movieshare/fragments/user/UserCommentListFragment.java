package com.example.movieshare.fragments.user;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.CommentAdapter;
import com.example.movieshare.databinding.FragmentUserCommentListBinding;
import com.example.movieshare.repository.MovieComment;
import com.example.movieshare.repository.Repository;

import java.util.List;

public class UserCommentListFragment extends Fragment {
    private FragmentUserCommentListBinding viewBindings;
    private List<MovieComment> userCommentList;
    private String userId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.viewBindings = FragmentUserCommentListBinding.inflate(inflater, container, false);

        // TODO: Initialize movieCommentList using Repository.instance.getAllMovieCommentsByUserId()
        this.userCommentList = Repository.instance().getAllMovieComments();

        RecyclerView userCommentsRecyclerList = this.viewBindings.userCommentListFragmentList;
        userCommentsRecyclerList.setHasFixedSize(true);
        userCommentsRecyclerList.setLayoutManager(new LinearLayoutManager(getContext()));
        CommentAdapter userCommentAdapter = new CommentAdapter(getLayoutInflater(), this.userCommentList);
        userCommentsRecyclerList.setAdapter(userCommentAdapter);

        userCommentAdapter.setOnItemClickListener(position -> {
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
}