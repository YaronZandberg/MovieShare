package com.example.movieshare.fragments.user;

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
import com.example.movieshare.databinding.FragmentUserCommentListBinding;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.utils.MovieUtils;
import com.example.movieshare.viewmodels.user.UserCommentListFragmentViewModel;

import java.util.Objects;

public class UserCommentListFragment extends Fragment {
    private FragmentUserCommentListBinding viewBindings;
    private String userId;
    private CommentAdapter userCommentAdapter;
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
        this.userCommentAdapter = new CommentAdapter(getLayoutInflater(), this.viewModel.getUserCommentList());
        this.viewBindings.userCommentListFragmentList.setAdapter(this.userCommentAdapter);
        configureMenuOptions();
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
                    this.userCommentAdapter.setMovieItemList(this.viewModel.getUserCommentList());
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
                        NavDirections action = UserCommentListFragmentDirections.actionGlobalUserProfileFragment();
                        Navigation.findNavController(viewBindings.getRoot()).navigate(action);
                        return true;
                    }
                }
                return false;
            }
        }, getViewLifecycleOwner(), Lifecycle.State.RESUMED);
    }
}