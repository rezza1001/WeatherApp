package com.rezza.weatherapp.libs;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;

import androidx.core.content.res.ResourcesCompat;

import com.rezza.weatherapp.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

    public static String readFileFromAsset(Context context, String file){
        StringBuilder text = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.getAssets().open(file)))) {
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                text.append(mLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    public static SpannableString BoldText(Context pContext, String pText, int start, int end, String colorCode){
        SpannableString content = new SpannableString(pText);
        Typeface font =  ResourcesCompat.getFont(pContext, R.font.roboto_bold);
        content.setSpan(new CustomTypefaceSpan("", font), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content.setSpan(new ForegroundColorSpan(Color.parseColor(colorCode)), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        return content;
    }
}
