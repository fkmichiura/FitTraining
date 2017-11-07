package com.fkmichiura.project.fittraining.Models;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class CurrentDate {

    private Calendar calendar;

    public CurrentDate() {
    }

    public String getCurrentTime(){

        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Brazil/East"));

        return simpleDateFormat.format(calendar.getTime());
    }

    public String getCurrentDate(){

        calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Brazil/East"));

        return simpleDateFormat.format(calendar.getTime());
    }
}
