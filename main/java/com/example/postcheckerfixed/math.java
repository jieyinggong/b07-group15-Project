package com.example.postcheckerfixed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class math extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math);

        findViewById(R.id.mathSpecButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(math.this, mathSpecial.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.mathMajorButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(math.this, mathMajor.class);
                startActivity(intent);
            }
        });
    }
}