package com.example.b07project;

public abstract class Information {
    public String infoID;
    protected String subject;
    protected String content;
    protected String submitID;

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


