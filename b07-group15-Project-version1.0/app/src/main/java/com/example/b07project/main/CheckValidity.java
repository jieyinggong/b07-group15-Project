package com.example.b07project.main;

public abstract class CheckValidity {

    /**
     * it returns true if the number of characters of the title is within the range 1-100
     * it returns false otherwise
     */
    public static boolean checkShortValidity(String title){
        if (title == null){
            return false;
        } else if (title.length() > 200 || title.length() == 0){
            return false;
        } else {
            return true;
        }
    }

    /**
     * it returns true if the number of characters of the content is within the range 1-5000
     * it returns false otherwise
     */
    public static boolean checkLongValidity(String content){
        if (content == null){
            return false;
        } else if (content.length() > 10000  || content.length() == 0){
            return false;
        } else {
            return true;
        }
    }
}
