package com.rezza.weatherapp.libs;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Utility {

    public static String getDateString(String sFormat){
        DateFormat format = new SimpleDateFormat(sFormat, Locale.getDefault());
        return format.format(new Date());
    }
    public static String getDateString(String sFormat,Date date){
        DateFormat format = new SimpleDateFormat(sFormat, Locale.getDefault());
        return format.format(date);
    }
    public static Date getDate(String sFormat,String date){
        DateFormat format = new SimpleDateFormat(sFormat, Locale.getDefault());
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return new Date();
        }
    }

    public static int getTimePart(Date date, int type){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(type);
    }

    public static int getQuadrant(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        if (calendar.get(Calendar.HOUR_OF_DAY) >= 3 && calendar.get(Calendar.HOUR_OF_DAY) < 6){
            return 2;
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) >= 6 && calendar.get(Calendar.HOUR_OF_DAY) < 9){
            return 3;
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) >= 9 && calendar.get(Calendar.HOUR_OF_DAY) < 12){
            return 4;
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) >= 12 && calendar.get(Calendar.HOUR_OF_DAY) < 15){
            return 5;
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) >= 15 && calendar.get(Calendar.HOUR_OF_DAY) < 18){
            return 6;
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) >= 18 && calendar.get(Calendar.HOUR_OF_DAY) < 21){
            return 7;
        }
        else if (calendar.get(Calendar.HOUR_OF_DAY) >= 21){
            return 8;
        }
        else {
            return 1;
        }
    }
}
