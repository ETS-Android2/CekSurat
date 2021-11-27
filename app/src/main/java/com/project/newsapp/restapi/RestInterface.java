package com.project.newsapp.restapi;

import com.project.newsapp.model.TotalSurat;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("v2/top-headlines")
    Call<TotalSurat> getTotalSurat(@Query("country") String country, @Query("apiKey") String apiKey);

    @GET("v2/top-headlines")
    Call<TotalSurat> getTotalSurat(@Query("country") String country, @Query("category") String category, @Query("apiKey") String apiKey);

    @GET("v2/everything")
    Call<TotalSurat> getSearchedTotalSurat(@Query("q") String country, @Query("apiKey") String apiKey);
}
