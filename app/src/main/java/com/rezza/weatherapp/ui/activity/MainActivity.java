package com.rezza.weatherapp.ui.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.rezza.weatherapp.R;
import com.rezza.weatherapp.api.model.WeatherModel;
import com.rezza.weatherapp.database.table.FavoriteDB;
import com.rezza.weatherapp.libs.Utility;
import com.rezza.weatherapp.ui.adapter.TimeAdapter;
import com.rezza.weatherapp.ui.dialog.FavoriteDialog;
import com.rezza.weatherapp.ui.dialog.FindCityDialog;
import com.rezza.weatherapp.ui.model.TimeHolder;
import com.rezza.weatherapp.ui.view.AfterTmrView;
import com.rezza.weatherapp.ui.view.TodayView;
import com.rezza.weatherapp.ui.view.TomorrowView;
import com.rezza.weatherapp.ui.viewmodel.WeatherViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TodayView vw_today;
    private TomorrowView vw_tomorrow;
    private AfterTmrView vw_afterTmr;
    private SwipeRefreshLayout srf_refresh;

    private   String city = "Jakarta";

    ArrayList<TimeHolder> lisTimeToday = new ArrayList<>();
    TimeAdapter mAdapter;

    private WeatherViewModel weatherViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        vw_today = findViewById(R.id.vw_today);
        vw_today.create();

        vw_tomorrow = findViewById(R.id.vw_tomorrow);
        vw_tomorrow.create();

        vw_afterTmr = findViewById(R.id.vw_afterTmr);
        vw_afterTmr.create();

        srf_refresh = findViewById(R.id.srf_refresh);

        RecyclerView rv_time = findViewById(R.id.rv_time);
        rv_time.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        mAdapter = new TimeAdapter(lisTimeToday);
        rv_time.setAdapter(mAdapter);

        FavoriteDB db = new FavoriteDB();
        ArrayList<FavoriteDB> listFavorite = db.getALl(this);
        if (listFavorite.size() > 0){
            city = listFavorite.get(0).name;
        }

        vw_today.setCity(city);

        weatherViewModel = new ViewModelProvider(this).get(WeatherViewModel.class);
        loadData();

        srf_refresh.setOnRefreshListener(this::loadData);
        vw_today.setOnSelectedCityListener(this::findCity);
        findViewById(R.id.rv_search).setOnClickListener(view -> findCity());
        findViewById(R.id.rv_favorite).setOnClickListener(view -> showFavorite());
    }

    public void loadData(){
        srf_refresh.setRefreshing(true);

        lisTimeToday.clear();
        weatherViewModel.loadData(city);
        weatherViewModel.getModelLiveData().observe(this, apiResponseModel -> {
            srf_refresh.setRefreshing(false);
            Log.d("Main","response code "+apiResponseModel.getCod());
            if (apiResponseModel.getCod().equals("200")){
               for (WeatherModel model: apiResponseModel.getList()){
                   buildDataToday(model);
                   buildTomorrow(model);
                   buildAfterTmr(model);
               }
            }
        });
    }

    @SuppressLint("NotifyDataSetChanged")
    private void buildDataToday(WeatherModel jo) {
        String sDate = jo.getDt_txt();
        Date date = Utility.getDate("yyyy-MM-dd HH:mm:ss", sDate);
        Date currDate = new Date();

        if (Utility.getTimePart(date,Calendar.DATE) != Utility.getTimePart(currDate,Calendar.DATE)){
            return;
        }

        WeatherModel.Main main = jo.getMain();
        WeatherModel.Wind wind = jo.getWind();
        WeatherModel.Weather weather = jo.getWeather().get(0);
        double temp = main.getTemp() - 273.15;

        String name = weather.getMain();

        if (Utility.getQuadrant(date) == Utility.getQuadrant(currDate)){

            vw_today.setTemperature(temp);
            vw_today.setHumidity(main.getHumidity());
            vw_today.setWind(wind.getGust());
            vw_today.setStatus(name);
        }
        Log.d("TAGRZ",sDate+ " = "+main.getTemp());
        lisTimeToday.add(new TimeHolder(Utility.getTimePart(date,Calendar.HOUR_OF_DAY),name,temp));
        mAdapter.notifyDataSetChanged();
    }

    private void buildTomorrow(WeatherModel jo) {
        String sDate = jo.getDt_txt();
        Date date = Utility.getDate("yyyy-MM-dd HH:mm:ss", sDate);
        Date currDate = vw_tomorrow.getDate();

        if (Utility.getTimePart(date,Calendar.DATE) != Utility.getTimePart(currDate,Calendar.DATE)){
            return;
        }

        if (Utility.getQuadrant(date) == Utility.getQuadrant(currDate)){
            WeatherModel.Main main = jo.getMain();
            WeatherModel.Wind wind = jo.getWind();
            WeatherModel.Weather weather = jo.getWeather().get(0);
            double temp = main.getTemp() - 273.15;
            String name = weather.getMain();

            vw_tomorrow.setTemperature(temp);
            vw_tomorrow.setHumidity(main.getHumidity());
            vw_tomorrow.setWind(wind.getGust());
            vw_tomorrow.setStatus(name);
        }
    }

    private void buildAfterTmr(WeatherModel jo){
        String sDate = jo.getDt_txt();
        Date date = Utility.getDate("yyyy-MM-dd HH:mm:ss", sDate);
        Date currDate = vw_afterTmr.getDate();

        if (Utility.getTimePart(date,Calendar.DATE) != Utility.getTimePart(currDate,Calendar.DATE)){
            return;
        }

        if (Utility.getQuadrant(date) == Utility.getQuadrant(currDate)){
            WeatherModel.Main main = jo.getMain();
            WeatherModel.Wind wind = jo.getWind();
            WeatherModel.Weather weather = jo.getWeather().get(0);
            double temp = main.getTemp() - 273.15;
            String name = weather.getMain();

            vw_afterTmr.setTemperature(temp);
            vw_afterTmr.setHumidity(main.getHumidity());
            vw_afterTmr.setWind(wind.getGust());
            vw_afterTmr.setStatus(name);
        }
    }

    private void findCity(){
        FindCityDialog dialog = new FindCityDialog(this);
        dialog.show();
        dialog.setOnSelectedListener(city -> {
            vw_today.setCity(city);
            this.city = city;
            loadData();
        });
    }

    private void showFavorite(){
        FavoriteDialog dialog = new FavoriteDialog(this);
        dialog.show();
        dialog.setOnSelectedListener(city -> {
            vw_today.setCity(city);
            this.city = city;
            loadData();
        });
    }

}