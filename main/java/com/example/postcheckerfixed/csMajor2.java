package com.example.postcheckerfixed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class csMajor2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cs_major2);

        findViewById(R.id.noButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(csMajor2.this, MainActivity.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(csMajor2.this, "Sorry, you don't qualify for this POSt yet",
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });

        findViewById(R.id.yesButton2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(csMajor2.this, MainActivity.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(csMajor2.this, "Congratulations!" +
                                " you're eligible to apply for this POSt!",
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
    }
}