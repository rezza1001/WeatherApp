package com.rezza.weatherapp.api.repository;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rezza.weatherapp.api.ApiConfig;
import com.rezza.weatherapp.api.CallApiService;
import com.rezza.weatherapp.api.model.ApiResponseModel;
import com.rezza.weatherapp.api.usecase.ApiInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForeCaseRepository {

    private final ApiInterface apiInterface;
    private MutableLiveData<ApiResponseModel> data;
    private Application application;


    public ForeCaseRepository(Application application) {
        this.application = application;
        CallApiService callApiService = new CallApiService();
        apiInterface = callApiService.getClient().create(ApiInterface.class);
    }

    public LiveData<ApiResponseModel> load(String city){
        data = new MutableLiveData<>();
        Log.d("ForeCaseRepository","Load "+city);
        apiInterface.loadForeCase(ApiConfig.GET_FORECAST,ApiConfig.API_KEY,city).enqueue(new Callback<ApiResponseModel>() {
            @Override
            public void onResponse(Call<ApiResponseModel> call, Response<ApiResponseModel> response) {
                Log.d("ForeCaseRepository","onResponse" +response.isSuccessful());
                ApiResponseModel resp = response.body();
                data.postValue(resp);
            }

            @Override
            public void onFailure(Call<ApiResponseModel> call, Throwable t) {
                ApiResponseModel resp = new ApiResponseModel();
                resp.setCod("400");
                data.postValue(resp);
                Log.e("ForeCaseRepository","onFailure "+ t.getMessage());
            }
        });
        return data;
    }
}
