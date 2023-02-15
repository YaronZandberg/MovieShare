package com.example.movieshare.fragments.movie;

import static com.example.movieshare.constants.MovieConstants.MOVIE_API_KEY;
import static com.example.movieshare.constants.MovieConstants.MOVIE_API_URL;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.movieshare.adapters.MovieAdapter;
import com.example.movieshare.constants.Categories;
import com.example.movieshare.databinding.FragmentMovieListBinding;
import com.example.movieshare.enums.LoadingState;
import com.example.movieshare.fragments.base.MovieBaseFragment;
import com.example.movieshare.notifications.NotificationManager;
import com.example.movieshare.repository.Repository;
import com.example.movieshare.repository.dao.MovieApiCaller;
import com.example.movieshare.repository.firebase.executors.MovieExecutor;
import com.example.movieshare.repository.models.Movie;
import com.example.movieshare.repository.models.MovieApi;
import com.example.movieshare.repository.models.MovieApiList;

import com.example.movieshare.viewmodels.movie.MovieListFragmentViewModel;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListFragment extends MovieBaseFragment {
    private FragmentMovieListBinding viewBindings;
    private Integer movieCategoryPosition;
    private MovieAdapter movieAdapter;
    private MovieListFragmentViewModel viewModel;
    private final Retrofit retrofit = new Retrofit.Builder().baseUrl(MOVIE_API_URL).addConverterFactory(GsonConverterFactory.create()).build();
    private final MovieApiCaller service = retrofit.create(MovieApiCaller.class);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.movieCategoryPosition = MovieListFragmentArgs.fromBundle(getArguments()).getMovieCategoryPosition();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        initializeMovieCategory();
        this.viewBindings = FragmentMovieListBinding.inflate(inflater, container, false);
        this.viewBindings.movieListFragmentMoviesList.setHasFixedSize(true);
        this.viewBindings.movieListFragmentMoviesList.setLayoutManager(new GridLayoutManager(getContext(), 3));
        this.movieAdapter = new MovieAdapter(getLayoutInflater(), this.viewModel.getMovieList().getValue());
        this.viewBindings.movieListFragmentMoviesList.setAdapter(this.movieAdapter);
        this.viewBindings.swipeRefresh.setOnRefreshListener(this::initializeMovieCategory);
        syncMoviesApiWithRemoteDb();
        this.configureMenuOptions(this.viewBindings.getRoot());
        activateItemListListener();
        NotificationManager.instance()
                .getEventMovieListLoadingState()
                .observe(getViewLifecycleOwner(),
                        loadingState -> this.viewBindings.swipeRefresh
                                .setRefreshing(loadingState == LoadingState.LOADING));
        return this.viewBindings.getRoot();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.viewModel = new ViewModelProvider(this).get(MovieListFragmentViewModel.class);
    }

    private void initializeMovieCategory() {
        Repository.getRepositoryInstance().refreshAllMovieCategories();
        this.viewModel.setMovieCategory(this.viewModel.getAllMovieCategories().getValue()
                .get(this.movieCategoryPosition));
        ((AppCompatActivity) getActivity()).getSupportActionBar()
                .setTitle(this.viewModel.getMovieCategory().getCategoryName());
        Repository.getRepositoryInstance().refreshAllMovies();
    }

    private void reloadMovieList() {
        if (Objects.nonNull(this.viewModel.getMovieList().getValue())) {
            Repository.getRepositoryInstance().getLocalModel().getMovieHandler()
                    .getAllMoviesByCategoryId(this.viewModel.getMovieCategory().getCategoryId(),
                            movieList -> this.movieAdapter.setMovieItemList(movieList));
        }
    }

    private void activateItemListListener() {
        this.movieAdapter.setOnItemClickListener(position -> {
            MovieListFragmentDirections
                    .ActionMovieListFragmentToMovieProfileFragment action =
                    MovieListFragmentDirections
                            .actionMovieListFragmentToMovieProfileFragment(position,
                                    this.viewModel.getMovieCategory().getCategoryId());
            Navigation.findNavController(viewBindings.movieListFragmentMoviesList).navigate(action);
        });
    }

    public void syncMoviesApiWithRemoteDb() {
        if (Objects.nonNull(this.viewModel.getMovieCategory()) && !this.viewModel.getMovieCategory().getCategoryId().equals("0") && !new Categories().getIdByName(this.viewModel.getMovieCategory().getCategoryName()).equals("0")) {
            this.service.getJson(MOVIE_API_KEY, new Categories().getIdByName(this.viewModel.getMovieCategory().getCategoryName())).enqueue((new Callback<MovieApiList>() {
                @Override
                public void onResponse(@NonNull Call<MovieApiList> call, @NonNull Response<MovieApiList> response) {
                    addMoviesToDb(response);
                }

                @Override
                public void onFailure(@NonNull Call<MovieApiList> call, @NonNull Throwable throwable) {
                    Log.d("apiError", throwable.toString());
                }
            }));
        }
        this.viewModel.getMovieList().observe(getViewLifecycleOwner(),
                movies -> Repository.getRepositoryInstance().refreshAllMovies());
        this.viewModel.getMovieList().observe(getViewLifecycleOwner(),
                movies -> reloadMovieList());
    }

    public void addMoviesToDb(Response<MovieApiList> response) {
        MovieExecutor executor = Repository.getRepositoryInstance().getFirebaseModel().getMovieExecutor();

        for (MovieApi item : response.body().getResults()) {
            executor.getMovieByName(item.getOriginal_title(), movies -> {
                if(movies.isEmpty() || movies.stream().filter(movie -> movie.getMovieCategoryId().contentEquals(this.viewModel.getMovieCategory().getCategoryId())).count() == 0) {
                    executor.addMovie(new Movie(this.viewModel.getMovieCategory().getCategoryId(), item.getOriginal_title(), item.getVote_average().toString(), item.getOverview(), item.getPoster_path()), () -> {});
                }
            });
        }
    }
}