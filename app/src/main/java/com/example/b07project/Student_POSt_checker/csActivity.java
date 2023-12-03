package com.example.b07project.Student_POSt_checker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.b07project.R;
import com.example.b07project.main.SetToolbar;

public class csActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_post_checker_cs);

        findViewById(R.id.msButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(csActivity.this, csMajor.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.minorButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(csActivity.this, csMinor.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);
    }
}