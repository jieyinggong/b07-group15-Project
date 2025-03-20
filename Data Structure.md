# Data Structure

Information 是基类

Complaint, Announcement, Event, Feedback extends Information 

```java
package com.example.b07project.main;

public class Information {
    public String infoID;
    public String subject;
    public String content;

    public Information(){}

    public Information(String subject, String content){
        this.subject = subject;
        this.content = content;
        infoID = "";
    }
}

public class Complaint extends Information {
        public Complaint(){
            super();
        }

        public Complaint(String subject, String content){
            super(subject,content);
        }

    }

public class Annoucement extends Information{
    public Annoucement(){
        super();
    }

    public Annoucement(String subject, String content){
        super(subject, content);
    }
}

public class Event extends Notification{
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

public class Feedback extends Information{
    int numericRating;
    String EventID;
    public Feedback(){
        super();
    }

    public Feedback(int numericRating, String comment, String EventID){
        super();
        this.numericRating = numericRating;
        this.content = comment;
        this.EventID = EventID;
        this.subject = "Feedback";
    }
}

```