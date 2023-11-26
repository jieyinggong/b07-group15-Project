package com.example.b07project.main;

import com.google.firebase.database.Exclude;

import java.time.LocalDateTime;

public class Event extends Information{
    String startDateTime;
    String endDateTime;
    String location;
    int CurrentAvailableSpace;


    public Event(){
        super();
        CurrentAvailableSpace = 0;
    };

}
