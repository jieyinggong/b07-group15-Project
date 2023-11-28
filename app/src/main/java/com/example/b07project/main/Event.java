package com.example.b07project.main;

import com.google.firebase.database.Exclude;

import java.time.LocalDateTime;

public class Event extends Information{
    public String startDateTime;
    public String endDateTime;
    public String location;
    public int CurrentAvailableSpace;


    public Event(){
        super();
        CurrentAvailableSpace = 0;
    };

}
