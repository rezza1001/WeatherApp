package com.rezza.weatherapp.api.model;

import java.io.Serializable;
import java.util.ArrayList;

public class ApiResponseModel implements Serializable {
    private String cod;
    private int message;
    private int cnt;
    private ArrayList<WeatherModel> list;

    public String getCod() {
        return cod;
    }

    public void setCod(String cod) {
        this.cod = cod;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public ArrayList<WeatherModel> getList() {
        return list;
    }

    public void setList(ArrayList<WeatherModel> list) {
        this.list = list;
    }
}
