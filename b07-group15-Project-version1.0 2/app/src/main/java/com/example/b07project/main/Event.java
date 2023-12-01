package com.example.b07project.main;

import androidx.compose.ui.graphics.vector.VectorProperty;

import com.google.firebase.database.Exclude;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Event extends Information{
    public String startDateTime;
    public String endDateTime;
    public String location;
    public int CurrentAvailableSpace;


    public Event(){
        super();
    };

    public static void AddCheckedEvent(Information info, List<Event> dataList){
        if (info instanceof Event && info.infoID != null) {
            Event event = (Event) info;
            dataList.add(0, event);
        }
    }

    public boolean eventNotStart() {
        try {
            Calendar startTime = ParseToCalendar.parseStringToCalendar(this.startDateTime);
            return Calendar.getInstance().before(startTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    public boolean eventInProgress() {
        try {
            Calendar startTime = ParseToCalendar.parseStringToCalendar(this.startDateTime);
            Calendar endTime = ParseToCalendar.parseStringToCalendar(this.endDateTime);
            return Calendar.getInstance().after(startTime) && Calendar.getInstance().before(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }

    public  boolean eventHasEnded() {
        try {
            Calendar endTime = ParseToCalendar.parseStringToCalendar(this.endDateTime);
            return Calendar.getInstance().after(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }


}
