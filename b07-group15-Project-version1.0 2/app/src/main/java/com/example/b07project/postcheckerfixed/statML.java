package com.example.b07project.postcheckerfixed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.b07project.R;
public class statML extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_ml);

        findViewById(R.id.MLyes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(statML.this, statML2.class);
                startActivity(intent);
            }

        });
        findViewById(R.id.MLno).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(statML.this, statSpecial.class);
                startActivity(intent);
            }

        });
    }
}