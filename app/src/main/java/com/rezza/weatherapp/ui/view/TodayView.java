package com.rezza.weatherapp.ui.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import com.rezza.weatherapp.R;
import com.rezza.weatherapp.libs.Utility;
import com.rezza.weatherapp.ui.master.MyView;

import java.text.DecimalFormat;
import java.util.Calendar;

public class TodayView extends MyView {

    protected TextView tv_date,tv_city,tv_humidity,tv_wind,tv_temperature,tv_status;
    private Calendar calendar;

    public TodayView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected int setLayout() {
        return R.layout.view_today;
    }

    @Override
    protected void initLayout() {
        tv_date = mView.findViewById(R.id.tv_date);
        tv_city = mView.findViewById(R.id.tv_city);
        tv_humidity = mView.findViewById(R.id.tv_humidity);
        tv_wind = mView.findViewById(R.id.tv_wind);
        tv_temperature = mView.findViewById(R.id.tv_temperature);
        tv_status = mView.findViewById(R.id.tv_status);
    }

    @Override
    protected void initListener() {

    }

    public void create() {
        super.create();

        tv_date.setText(Utility.getDateString("dd/MM/yyyy"));
        calendar = Calendar.getInstance();
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.MILLISECOND,0);
    }

    public void setCity(String city){
        tv_city.setText(city);
    }

    public void setHumidity(int humidity){
        String sdata = humidity +" %RH";
        tv_humidity.setText(sdata);
    }

    public void setWind(double wind){
        String sdata = wind +" m/s";
        tv_wind.setText(sdata);
    }

    public void setTemperature(double temperature){
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        tv_temperature.setText(decimalFormat.format(temperature));
    }

    public String getTime(){
        return Utility.getDateString("yyyy-MM-dd HH:mm:ss", calendar.getTime());
    }

    public void setStatus(String status){
        tv_status.setText(status);
    }
}
