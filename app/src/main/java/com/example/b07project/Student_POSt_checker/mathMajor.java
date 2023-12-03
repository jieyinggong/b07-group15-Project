package com.example.b07project.Student_POSt_checker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.b07project.R;
import com.example.b07project.main.SetToolbar;

public class mathMajor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_post_checker_math_major);
        findViewById(R.id.mathNo2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mathMajor.this, CMSpostMain.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(mathMajor.this, "Sorry, you don't qualify for this POSt yet",
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });

        findViewById(R.id.mathYes2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mathMajor.this, mathMajor2.class);
                startActivity(intent);
            }
        });

        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);
    }
}