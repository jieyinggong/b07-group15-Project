package com.example.b07project.main;

import com.google.firebase.database.Exclude;

import java.time.LocalDateTime;

public class Event extends Information{
    String dateString;
    String location;
    int CurrentAvailableSpace;
    boolean SpaceLimit;
    @Exclude
    LocalDateTime EventDateTime;

    public Event(){
        super();
        SpaceLimit = false;
        CurrentAvailableSpace = -1;
        location = "";
    };

}
