package com.example.b07project.main;

public class Feedback extends Information{
    public int numericRating;
    public String EventID;
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
