package com.rezza.weatherapp.ui.viewmodel;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rezza.weatherapp.api.model.ApiResponseModel;
import com.rezza.weatherapp.api.repository.ForeCaseRepository;

public class WeatherViewModel extends AndroidViewModel{
    private final ForeCaseRepository repository;
    private LiveData<ApiResponseModel> modelLiveData ;

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        repository = new ForeCaseRepository(application);
        modelLiveData = new MutableLiveData<>();
    }

    public void loadData(String city){
        modelLiveData = repository.load(city);
    }

    public LiveData<ApiResponseModel> getModelLiveData() {
        return modelLiveData;
    }
}
