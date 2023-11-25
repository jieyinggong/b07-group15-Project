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

    public void getInfoID(String id){
        infoID = id;
    }


    /**
     * it returns true if the number of characters of the title is within the range 1-100
     * it returns false otherwise
     */
    public boolean checkTitleValidity(String title){
        if (title == null){
            return false;
        } else if (title.length() > 100){
            return false;
        } else {
            return true;
        }
    }

    /**
     * it returns true if the number of characters of the content is within the range 1-5000
     * it returns false otherwise
     */
    public boolean checkContentValidity(String content){
        if (content == null){
            return false;
        } else if (content.length() > 5000){
            return false;
        } else {
            return true;
        }
    }
}


