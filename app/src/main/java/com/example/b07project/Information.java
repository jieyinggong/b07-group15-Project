package com.example.b07project;

public class Information {
    public String infoID;
    public String subject;
    public String content;
    public String submitID;

    public Information(){}

    public Information(String subject, String content){
        this.subject = subject;
        this.content = content;
        infoID = "";
        submitID = "";
    }

    public void getInfoID(String id){
        infoID = id;
    }
}


