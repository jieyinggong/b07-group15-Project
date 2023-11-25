package com.example.b07project.main;

public abstract class CheckValidity {

    /**
     * it returns true if the number of characters of the title is within the range 1-100
     * it returns false otherwise
     */
    public static boolean checkTitleValidity(String title){
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
    public static boolean checkContentValidity(String content){
        if (content == null){
            return false;
        } else if (content.length() > 5000){
            return false;
        } else {
            return true;
        }
    }
}
