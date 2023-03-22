package com.rezza.weatherapp.api.usecase;

import com.rezza.weatherapp.api.model.ApiResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {

    @GET
    public Call<ApiResponseModel> loadForeCase(@Url String url,  @Query("appid") String appid,  @Query("q") String q);

}
