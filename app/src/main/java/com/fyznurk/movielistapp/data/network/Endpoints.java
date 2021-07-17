package com.fyznurk.movielistapp.data.network;

import com.fyznurk.movielistapp.data.models.PopularMoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface Endpoints {

    @GET("movie/popular")
    Call<PopularMoviesResponse> getPopularMovies(@Query("api_key") String apiKey);
}
