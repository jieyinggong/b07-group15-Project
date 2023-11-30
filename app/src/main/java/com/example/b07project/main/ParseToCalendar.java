package com.example.b07project.main;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class ParseToCalendar {
    public static Calendar parseStringToCalendar(String timeString) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Date parse = dateFormat.parse(timeString);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (parse != null){
            calendar.setTime(parse);}
        return calendar;
    }
}
