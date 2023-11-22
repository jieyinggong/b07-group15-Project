package com.example.postcheckerfixed;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class statML2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_ml2);
        findViewById(R.id.MLyes2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(statML2.this, statSpecial.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.MLno2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(statML2.this, MainActivity.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(statML2.this, "Sorry, you don't qualify for the Machine Learning Branch",
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
    }
}