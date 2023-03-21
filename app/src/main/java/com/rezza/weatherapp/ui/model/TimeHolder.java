package com.rezza.weatherapp.ui.model;

import java.io.Serializable;

public class TimeHolder implements Serializable {

    private int time;
    private String name;
    private double temperature;

    public TimeHolder(int time, String name, double temperature) {
        this.time = time;
        this.name = name;
        this.temperature = temperature;
    }

    public int getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }
}
