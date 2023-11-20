package com.example.b07project.dbOperation_o;


import android.util.Log;

public class PrintCallback implements DefaultCallback {
        @Override
        public void onSuccess() {
            Log.i("TAG", "onSuccess: Success!");
        }

        @Override
        public void onFailure(Exception e) {
            Log.i("TAG","Error:"  + e.getMessage());
        }
}

