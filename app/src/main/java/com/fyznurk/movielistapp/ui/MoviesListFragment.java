package com.fyznurk.movielistapp.ui;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fyznurk.movielistapp.R;
import com.fyznurk.movielistapp.adapter.MovieListAdapter;
import com.fyznurk.movielistapp.adapter.RecyclerItemClick;
import com.fyznurk.movielistapp.data.models.MovieDetail;
import com.fyznurk.movielistapp.data.models.PopularMoviesResponse;
import com.fyznurk.movielistapp.data.network.Api;
import com.fyznurk.movielistapp.data.network.Endpoints;
import com.fyznurk.movielistapp.utils.CustomDialog;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.fyznurk.movielistapp.utils.Constants.apiKey;

public class MoviesListFragment extends Fragment implements RecyclerItemClick {

    private ArrayList<MovieDetail> movieList;
    private Endpoints endpoints;
    private MovieListAdapter movieListAdapter;



    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.rvList)
    RecyclerView rvList;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.spinner)
    Spinner spinner;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.progress)
    ProgressBar progressBar;

    @SuppressLint("NonConstantResourceId")
    @BindView(R.id.edtSearch)
    EditText edtSearch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_movies_list, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        endpoints = new Api().endpoints();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvList.setLayoutManager(linearLayoutManager);
        movieList = new ArrayList<>();
        setSpinner();
        getList();
        editTextWatcher();

    }

    private void editTextWatcher() {
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                movieListAdapter.getFilter().filter(edtSearch.getText().toString());
            }
        });
    }

    private void setSpinner() {
        String[] orderList = new String[]{"Order By", "Desc Popularity", "Asc Popularity"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, orderList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (movieList.size() != 0) {
                    switch (i) {
                        case 2:
                            getAscOrder();
                            getOrderedList(movieList);
                            break;
                        case 1:
                            getDescOrder();
                           // Collections.sort(descList, (movieDetail, t1) -> Double.compare(movieDetail.getPopularity(), t1.getPopularity()));
                            getOrderedList(movieList);
                            break;
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void getAscOrder(){
        Collections.sort(movieList, (movieDetail, t1) -> Double.compare(movieDetail.getPopularity(), t1.getPopularity()));
    }
    private void getDescOrder(){
        Collections.sort(movieList, (movieDetail, t1) -> Double.compare(t1.getPopularity(), movieDetail.getPopularity()));
    }


    @SuppressLint("CheckResult")
    private void getList() {
        progressBar.setVisibility(View.VISIBLE);
        endpoints.getPopularMovies(apiKey)
                .enqueue(new Callback<PopularMoviesResponse>(){
                    @Override
                    public void onResponse(@NotNull Call<PopularMoviesResponse> call, @NotNull Response<PopularMoviesResponse> response) {
                        progressBar.setVisibility(View.GONE);
                        if (response.isSuccessful()) {
                            assert response.body() != null;
                            movieList = response.body().getResults();
                        //    descList = movieList;
                        //    Collections.sort(descList, (movieDetail, t1) -> Double.compare(movieDetail.getPopularity(), t1.getPopularity()));
                            getOrderedList(movieList);
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<PopularMoviesResponse> call, @NotNull Throwable t) {
                        progressBar.setVisibility(View.GONE);
                        CustomDialog dialog=new CustomDialog(requireContext(),t.getMessage());
                        dialog.show();
                    }
                });

    }


    private void getOrderedList(ArrayList<MovieDetail> list) {
        movieListAdapter = new MovieListAdapter(list, this);
        rvList.setAdapter(movieListAdapter);
        movieListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(Integer position) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", movieListAdapter.filteredData.get(position));
        Navigation.findNavController(requireView()).navigate(R.id.action_moviesListFragment_to_movieDetailFragment, bundle);
    }
}
