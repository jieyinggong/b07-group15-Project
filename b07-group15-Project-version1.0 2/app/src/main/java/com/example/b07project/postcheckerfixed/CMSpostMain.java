package com.example.b07project.postcheckerfixed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import com.example.b07project.R;

public class CMSpostMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cmsstart);


        findViewById(R.id.csButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CMSpostMain.this, csActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.mathButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CMSpostMain.this, math.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.statButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CMSpostMain.this, stat.class);
                startActivity(intent);
            }
        });
    }
}