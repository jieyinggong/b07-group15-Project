package com.example.b07project.Student_Starter;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.SharedPreferences;

public class Student {
    public String username;
    public String fullName;
    public String passwordHash;

    public Student() {
    }

    public Student(String username, String fullName, String passwordHash) {
        this.username = username;
        this.fullName = fullName;
        this.passwordHash = passwordHash;
    }

    public static void  getUsername(Activity activity,String username){
        SharedPreferences studentPreference = activity.getSharedPreferences("StudentPref", MODE_PRIVATE);
        SharedPreferences.Editor editor = studentPreference.edit();
        editor.putString("username",username);
        editor.apply();
    }
}