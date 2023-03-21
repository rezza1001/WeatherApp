package com.rezza.weatherapp.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import com.rezza.weatherapp.R;
import com.rezza.weatherapp.database.table.FavoriteDB;
import com.rezza.weatherapp.libs.Utility;
import com.rezza.weatherapp.ui.adapter.TimeAdapter;
import com.rezza.weatherapp.ui.dialog.FavoriteDialog;
import com.rezza.weatherapp.ui.dialog.FindCityDialog;
import com.rezza.weatherapp.ui.model.TimeHolder;
import com.rezza.weatherapp.ui.view.AfterTmrView;
import com.rezza.weatherapp.ui.view.TodayView;
import com.rezza.weatherapp.ui.view.TomorrowView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TodayView vw_today;
    private TomorrowView vw_tomorrow;
    private AfterTmrView vw_afterTmr;

    private   String city = "Jakarta";

    ArrayList<TimeHolder> lisTimeToday = new ArrayList<>();
    TimeAdapter mAdapter;

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

        RecyclerView rv_time = findViewById(R.id.rv_time);
        rv_time.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));
        mAdapter = new TimeAdapter(lisTimeToday);
        rv_time.setAdapter(mAdapter);

        findViewById(R.id.rv_search).setOnClickListener(view -> findCity());
        findViewById(R.id.rv_favorite).setOnClickListener(view -> showFavorite());


        FavoriteDB db = new FavoriteDB();
        ArrayList<FavoriteDB> listFavorite = db.getALl(this);
        if (listFavorite.size() > 0){
            city = listFavorite.get(0).name;
        }

        vw_today.setCity(city);
        loadData();
    }

    public void loadData(){
        lisTimeToday.clear();
        String strData = "{\"cod\":\"200\",\"message\":0,\"cnt\":40,\"list\":[{\"dt\":1679389200,\"main\":{\"temp\":300.69,\"feels_like\":302.95,\"temp_min\":300.69,\"temp_max\":300.69,\"pressure\":1006,\"sea_level\":1006,\"grnd_level\":974,\"humidity\":70,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":33},\"wind\":{\"speed\":1.38,\"deg\":220,\"gust\":1.75},\"visibility\":10000,\"pop\":0.74,\"rain\":{\"3h\":0.94},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-21 09:00:00\"},{\"dt\":1679400000,\"main\":{\"temp\":299.81,\"feels_like\":299.81,\"temp_min\":298.04,\"temp_max\":299.81,\"pressure\":1007,\"sea_level\":1007,\"grnd_level\":976,\"humidity\":75,\"temp_kf\":1.77},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":44},\"wind\":{\"speed\":1.17,\"deg\":44,\"gust\":1.42},\"visibility\":10000,\"pop\":0.93,\"rain\":{\"3h\":2.36},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-21 12:00:00\"},{\"dt\":1679410800,\"main\":{\"temp\":298.68,\"feels_like\":299.38,\"temp_min\":297.67,\"temp_max\":298.68,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":978,\"humidity\":80,\"temp_kf\":1.01},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":77},\"wind\":{\"speed\":1.22,\"deg\":75,\"gust\":1.37},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":0.88},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-21 15:00:00\"},{\"dt\":1679421600,\"main\":{\"temp\":296.73,\"feels_like\":297.42,\"temp_min\":296.73,\"temp_max\":296.73,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":976,\"humidity\":87,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":99},\"wind\":{\"speed\":1.8,\"deg\":76,\"gust\":1.88},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":2.67},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-21 18:00:00\"},{\"dt\":1679432400,\"main\":{\"temp\":296.44,\"feels_like\":297.12,\"temp_min\":296.44,\"temp_max\":296.44,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":976,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":501,\"main\":\"Rain\",\"description\":\"moderate rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.66,\"deg\":54,\"gust\":1.71},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":4.36},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-21 21:00:00\"},{\"dt\":1679443200,\"main\":{\"temp\":296.94,\"feels_like\":297.65,\"temp_min\":296.94,\"temp_max\":296.94,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":978,\"humidity\":87,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":98},\"wind\":{\"speed\":1.69,\"deg\":89,\"gust\":2.08},\"visibility\":10000,\"pop\":1,\"rain\":{\"3h\":1.09},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-22 00:00:00\"},{\"dt\":1679454000,\"main\":{\"temp\":300.85,\"feels_like\":303.13,\"temp_min\":300.85,\"temp_max\":300.85,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":978,\"humidity\":69,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":1.7,\"deg\":135,\"gust\":2.27},\"visibility\":10000,\"pop\":0.27,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-22 03:00:00\"},{\"dt\":1679464800,\"main\":{\"temp\":299.01,\"feels_like\":299.71,\"temp_min\":299.01,\"temp_max\":299.01,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":976,\"humidity\":79,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":95},\"wind\":{\"speed\":1.55,\"deg\":157,\"gust\":2.06},\"visibility\":10000,\"pop\":0.39,\"rain\":{\"3h\":0.11},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-22 06:00:00\"},{\"dt\":1679475600,\"main\":{\"temp\":299.62,\"feels_like\":299.62,\"temp_min\":299.62,\"temp_max\":299.62,\"pressure\":1007,\"sea_level\":1007,\"grnd_level\":975,\"humidity\":76,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":96},\"wind\":{\"speed\":1.9,\"deg\":145,\"gust\":2.48},\"visibility\":10000,\"pop\":0.37,\"rain\":{\"3h\":0.58},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-22 09:00:00\"},{\"dt\":1679486400,\"main\":{\"temp\":297.64,\"feels_like\":298.39,\"temp_min\":297.64,\"temp_max\":297.64,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":975,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":91},\"wind\":{\"speed\":2.28,\"deg\":113,\"gust\":3.04},\"visibility\":10000,\"pop\":0.33,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-22 12:00:00\"},{\"dt\":1679497200,\"main\":{\"temp\":297.12,\"feels_like\":297.9,\"temp_min\":297.12,\"temp_max\":297.12,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":977,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.07,\"deg\":103,\"gust\":2.37},\"visibility\":10000,\"pop\":0.01,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-22 15:00:00\"},{\"dt\":1679508000,\"main\":{\"temp\":296.74,\"feels_like\":297.5,\"temp_min\":296.74,\"temp_max\":296.74,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":976,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.77,\"deg\":79,\"gust\":1.85},\"visibility\":10000,\"pop\":0.12,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-22 18:00:00\"},{\"dt\":1679518800,\"main\":{\"temp\":296.48,\"feels_like\":297.19,\"temp_min\":296.48,\"temp_max\":296.48,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":975,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":89},\"wind\":{\"speed\":1.54,\"deg\":33,\"gust\":1.54},\"visibility\":10000,\"pop\":0.28,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-22 21:00:00\"},{\"dt\":1679529600,\"main\":{\"temp\":297.54,\"feels_like\":298.25,\"temp_min\":297.54,\"temp_max\":297.54,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":976,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":1.2,\"deg\":16,\"gust\":1.56},\"visibility\":10000,\"pop\":0.29,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-23 00:00:00\"},{\"dt\":1679540400,\"main\":{\"temp\":301.99,\"feels_like\":304.85,\"temp_min\":301.99,\"temp_max\":301.99,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":978,\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":99},\"wind\":{\"speed\":2.3,\"deg\":282,\"gust\":2.26},\"visibility\":10000,\"pop\":0.25,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-23 03:00:00\"},{\"dt\":1679551200,\"main\":{\"temp\":302.07,\"feels_like\":305,\"temp_min\":302.07,\"temp_max\":302.07,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":976,\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.78,\"deg\":257,\"gust\":3.66},\"visibility\":10000,\"pop\":0.51,\"rain\":{\"3h\":0.86},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-23 06:00:00\"},{\"dt\":1679562000,\"main\":{\"temp\":300.59,\"feels_like\":302.98,\"temp_min\":300.59,\"temp_max\":300.59,\"pressure\":1007,\"sea_level\":1007,\"grnd_level\":975,\"humidity\":72,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":2.91,\"deg\":268,\"gust\":3.35},\"visibility\":10000,\"pop\":0.6,\"rain\":{\"3h\":0.91},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-23 09:00:00\"},{\"dt\":1679572800,\"main\":{\"temp\":298.14,\"feels_like\":298.86,\"temp_min\":298.14,\"temp_max\":298.14,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":977,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":99},\"wind\":{\"speed\":0.98,\"deg\":277,\"gust\":1.27},\"visibility\":10000,\"pop\":0.64,\"rain\":{\"3h\":1.44},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-23 12:00:00\"},{\"dt\":1679583600,\"main\":{\"temp\":297.37,\"feels_like\":298.15,\"temp_min\":297.37,\"temp_max\":297.37,\"pressure\":1011,\"sea_level\":1011,\"grnd_level\":978,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":97},\"wind\":{\"speed\":1.08,\"deg\":26,\"gust\":1.05},\"visibility\":10000,\"pop\":0.46,\"rain\":{\"3h\":1.02},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-23 15:00:00\"},{\"dt\":1679594400,\"main\":{\"temp\":296.76,\"feels_like\":297.53,\"temp_min\":296.76,\"temp_max\":296.76,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":977,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":90},\"wind\":{\"speed\":1.49,\"deg\":4,\"gust\":1.55},\"visibility\":10000,\"pop\":0.33,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-23 18:00:00\"},{\"dt\":1679605200,\"main\":{\"temp\":296.34,\"feels_like\":297.06,\"temp_min\":296.34,\"temp_max\":296.34,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":976,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":67},\"wind\":{\"speed\":1.3,\"deg\":27,\"gust\":1.3},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-23 21:00:00\"},{\"dt\":1679616000,\"main\":{\"temp\":297.21,\"feels_like\":297.92,\"temp_min\":297.21,\"temp_max\":297.21,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":978,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":62},\"wind\":{\"speed\":0.91,\"deg\":53,\"gust\":1.01},\"visibility\":10000,\"pop\":0.09,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-24 00:00:00\"},{\"dt\":1679626800,\"main\":{\"temp\":301.77,\"feels_like\":304.58,\"temp_min\":301.77,\"temp_max\":301.77,\"pressure\":1011,\"sea_level\":1011,\"grnd_level\":978,\"humidity\":67,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03d\"}],\"clouds\":{\"all\":45},\"wind\":{\"speed\":1.5,\"deg\":278,\"gust\":1.21},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-24 03:00:00\"},{\"dt\":1679637600,\"main\":{\"temp\":300.72,\"feels_like\":303.46,\"temp_min\":300.72,\"temp_max\":300.72,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":976,\"humidity\":74,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":42},\"wind\":{\"speed\":3.64,\"deg\":247,\"gust\":3.27},\"visibility\":10000,\"pop\":0.36,\"rain\":{\"3h\":0.48},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-24 06:00:00\"},{\"dt\":1679648400,\"main\":{\"temp\":300.55,\"feels_like\":302.8,\"temp_min\":300.55,\"temp_max\":300.55,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":976,\"humidity\":71,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":42},\"wind\":{\"speed\":2.35,\"deg\":241,\"gust\":2.38},\"visibility\":10000,\"pop\":0.56,\"rain\":{\"3h\":1.2},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-24 09:00:00\"},{\"dt\":1679659200,\"main\":{\"temp\":298.15,\"feels_like\":298.85,\"temp_min\":298.15,\"temp_max\":298.15,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":977,\"humidity\":82,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":68},\"wind\":{\"speed\":1.04,\"deg\":254,\"gust\":1.24},\"visibility\":10000,\"pop\":0.56,\"rain\":{\"3h\":0.4},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-24 12:00:00\"},{\"dt\":1679670000,\"main\":{\"temp\":297.61,\"feels_like\":298.33,\"temp_min\":297.61,\"temp_max\":297.61,\"pressure\":1011,\"sea_level\":1011,\"grnd_level\":979,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":77},\"wind\":{\"speed\":0.73,\"deg\":21,\"gust\":0.89},\"visibility\":10000,\"pop\":0.29,\"rain\":{\"3h\":0.13},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-24 15:00:00\"},{\"dt\":1679680800,\"main\":{\"temp\":297.27,\"feels_like\":297.98,\"temp_min\":297.27,\"temp_max\":297.27,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":977,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":89},\"wind\":{\"speed\":1.04,\"deg\":5,\"gust\":1.06},\"visibility\":10000,\"pop\":0.25,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-24 18:00:00\"},{\"dt\":1679691600,\"main\":{\"temp\":296.83,\"feels_like\":297.53,\"temp_min\":296.83,\"temp_max\":296.83,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":977,\"humidity\":87,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":0.89,\"deg\":32,\"gust\":0.91},\"visibility\":10000,\"pop\":0.13,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-24 21:00:00\"},{\"dt\":1679702400,\"main\":{\"temp\":297.49,\"feels_like\":298.2,\"temp_min\":297.49,\"temp_max\":297.49,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":978,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":98},\"wind\":{\"speed\":1.06,\"deg\":14,\"gust\":1.18},\"visibility\":10000,\"pop\":0.08,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-25 00:00:00\"},{\"dt\":1679713200,\"main\":{\"temp\":301.61,\"feels_like\":303.84,\"temp_min\":301.61,\"temp_max\":301.61,\"pressure\":1011,\"sea_level\":1011,\"grnd_level\":979,\"humidity\":64,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":79},\"wind\":{\"speed\":1.31,\"deg\":249,\"gust\":0.89},\"visibility\":10000,\"pop\":0.11,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-25 03:00:00\"},{\"dt\":1679724000,\"main\":{\"temp\":301.75,\"feels_like\":304.54,\"temp_min\":301.75,\"temp_max\":301.75,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":976,\"humidity\":67,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":81},\"wind\":{\"speed\":3.53,\"deg\":244,\"gust\":2.35},\"visibility\":10000,\"pop\":0.27,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-25 06:00:00\"},{\"dt\":1679734800,\"main\":{\"temp\":300.29,\"feels_like\":302.52,\"temp_min\":300.29,\"temp_max\":300.29,\"pressure\":1007,\"sea_level\":1007,\"grnd_level\":975,\"humidity\":73,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":92},\"wind\":{\"speed\":2.88,\"deg\":245,\"gust\":2.64},\"visibility\":10000,\"pop\":0.47,\"rain\":{\"3h\":0.73},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-25 09:00:00\"},{\"dt\":1679745600,\"main\":{\"temp\":297.86,\"feels_like\":298.61,\"temp_min\":297.86,\"temp_max\":297.86,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":976,\"humidity\":85,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":95},\"wind\":{\"speed\":0.97,\"deg\":215,\"gust\":1.09},\"visibility\":10000,\"pop\":0.64,\"rain\":{\"3h\":0.66},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-25 12:00:00\"},{\"dt\":1679756400,\"main\":{\"temp\":297.53,\"feels_like\":298.27,\"temp_min\":297.53,\"temp_max\":297.53,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":978,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":0.58,\"deg\":37,\"gust\":0.64},\"visibility\":10000,\"pop\":0.73,\"rain\":{\"3h\":0.6},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-25 15:00:00\"},{\"dt\":1679767200,\"main\":{\"temp\":296.76,\"feels_like\":297.5,\"temp_min\":296.76,\"temp_max\":296.76,\"pressure\":1009,\"sea_level\":1009,\"grnd_level\":976,\"humidity\":89,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.49,\"deg\":336,\"gust\":1.5},\"visibility\":10000,\"pop\":0.76,\"rain\":{\"3h\":0.58},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-25 18:00:00\"},{\"dt\":1679778000,\"main\":{\"temp\":296.4,\"feels_like\":297.13,\"temp_min\":296.4,\"temp_max\":296.4,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":976,\"humidity\":90,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10n\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.37,\"deg\":349,\"gust\":1.39},\"visibility\":10000,\"pop\":0.61,\"rain\":{\"3h\":0.4},\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-03-25 21:00:00\"},{\"dt\":1679788800,\"main\":{\"temp\":297.1,\"feels_like\":297.85,\"temp_min\":297.1,\"temp_max\":297.1,\"pressure\":1010,\"sea_level\":1010,\"grnd_level\":978,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":1.99,\"deg\":341,\"gust\":2.9},\"visibility\":10000,\"pop\":0.45,\"rain\":{\"3h\":0.12},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-26 00:00:00\"},{\"dt\":1679799600,\"main\":{\"temp\":300.03,\"feels_like\":302.04,\"temp_min\":300.03,\"temp_max\":300.03,\"pressure\":1011,\"sea_level\":1011,\"grnd_level\":978,\"humidity\":73,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.23,\"deg\":294,\"gust\":3.77},\"visibility\":10000,\"pop\":0.24,\"rain\":{\"3h\":0.11},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-26 03:00:00\"},{\"dt\":1679810400,\"main\":{\"temp\":300.74,\"feels_like\":302.93,\"temp_min\":300.74,\"temp_max\":300.74,\"pressure\":1008,\"sea_level\":1008,\"grnd_level\":976,\"humidity\":69,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":100},\"wind\":{\"speed\":3.91,\"deg\":282,\"gust\":4.22},\"visibility\":10000,\"pop\":0.37,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-03-26 06:00:00\"}],\"city\":{\"id\":1636133,\"name\":\"Cimarelang\",\"coord\":{\"lat\":-7.2736,\"lon\":106.5319},\"country\":\"ID\",\"population\":1000,\"timezone\":25200,\"sunrise\":1679353074,\"sunset\":1679396676}}";
        try {
            JSONObject jo = new JSONObject(strData);
            JSONArray list = jo.getJSONArray("list");
            for (int i=0; i<list.length(); i++){
                JSONObject weather = list.getJSONObject(i);
                buildDataToday(weather);
                buildTomorrow(weather);
                buildAfterTmr(weather);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private void buildDataToday(JSONObject jo) throws JSONException {
        String sDate = jo.getString("dt_txt");
        Date date = Utility.getDate("yyyy-MM-dd HH:mm:ss", sDate);
        Date currDate = new Date();

        if (Utility.getTimePart(date,Calendar.DATE) != Utility.getTimePart(currDate,Calendar.DATE)){
            return;
        }

        JSONObject main = jo.getJSONObject("main");
        JSONObject wind = jo.getJSONObject("wind");
        JSONArray weather = jo.getJSONArray("weather");
        double temp = main.getDouble("temp") - 273.15;
        String name = "-";
        if (weather.length() > 0){
            name = weather.getJSONObject(0).getString("main");
        }

        if (Utility.getQuadrant(date) == Utility.getQuadrant(currDate)){
            vw_today.setTemperature(temp);
            vw_today.setHumidity(main.getInt("humidity"));
            vw_today.setWind(wind.getDouble("gust"));
            vw_today.setStatus(name);
        }
        lisTimeToday.add(new TimeHolder(Utility.getTimePart(date,Calendar.HOUR_OF_DAY),name,temp));
        mAdapter.notifyDataSetChanged();
    }

    private void buildTomorrow(JSONObject jo) throws JSONException {
        String sDate = jo.getString("dt_txt");
        Date date = Utility.getDate("yyyy-MM-dd HH:mm:ss", sDate);
        Date currDate = vw_tomorrow.getDate();

        if (Utility.getTimePart(date,Calendar.DATE) != Utility.getTimePart(currDate,Calendar.DATE)){
            return;
        }

        if (Utility.getQuadrant(date) == Utility.getQuadrant(currDate)){
            JSONObject main = jo.getJSONObject("main");
            JSONObject wind = jo.getJSONObject("wind");
            JSONArray weather = jo.getJSONArray("weather");

            double temp = main.getDouble("temp") - 273.15;
            vw_tomorrow.setTemperature(temp);
            vw_tomorrow.setHumidity(main.getInt("humidity"));
            vw_tomorrow.setWind(wind.getDouble("gust"));
            if (weather.length() > 0){
                vw_tomorrow.setStatus(weather.getJSONObject(0).getString("main"));
            }
        }
    }

    private void buildAfterTmr(JSONObject jo) throws JSONException {
        String sDate = jo.getString("dt_txt");
        Date date = Utility.getDate("yyyy-MM-dd HH:mm:ss", sDate);
        Date currDate = vw_afterTmr.getDate();

        if (Utility.getTimePart(date,Calendar.DATE) != Utility.getTimePart(currDate,Calendar.DATE)){
            return;
        }

        if (Utility.getQuadrant(date) == Utility.getQuadrant(currDate)){
            JSONObject main = jo.getJSONObject("main");
            JSONObject wind = jo.getJSONObject("wind");
            JSONArray weather = jo.getJSONArray("weather");

            double temp = main.getDouble("temp") - 273.15;
            vw_afterTmr.setTemperature(temp);
            vw_afterTmr.setHumidity(main.getInt("humidity"));
            vw_afterTmr.setWind(wind.getDouble("gust"));
            if (weather.length() > 0){
                vw_afterTmr.setStatus(weather.getJSONObject(0).getString("main"));
            }
        }
    }

    private void findCity(){
        FindCityDialog dialog = new FindCityDialog(this);
        dialog.show();
        dialog.setOnSelectedListener(city -> {
            vw_today.setCity(city);
            loadData();
        });
    }

    private void showFavorite(){
        FavoriteDialog dialog = new FavoriteDialog(this);
        dialog.show();
        dialog.setOnSelectedListener(city -> {
            vw_today.setCity(city);
            loadData();
        });
    }

}