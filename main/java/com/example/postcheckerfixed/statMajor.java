package com.example.postcheckerfixed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class statMajor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_major);

        findViewById(R.id.statMajorNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(statMajor.this, MainActivity.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(statMajor.this, "Sorry, you don't qualify for this POSt yet",
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });

        findViewById(R.id.statMajorYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(statMajor.this, MainActivity.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(statMajor.this, "Congratulations!" +
                                " you're eligible to apply for this POSt!",
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
    }
}