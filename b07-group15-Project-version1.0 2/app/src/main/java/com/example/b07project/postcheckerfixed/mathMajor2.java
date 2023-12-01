package com.example.b07project.postcheckerfixed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.b07project.R;
public class mathMajor2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_major2);

        findViewById(R.id.mathNo4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mathMajor2.this, CMSpostMain.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(mathMajor2.this, "Sorry, you don't qualify for this POSt yet",
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });

        findViewById(R.id.mathYes4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mathMajor2.this, CMSpostMain.class);
                startActivity(intent);
                Toast myToast = Toast.makeText(mathMajor2.this, "Congratulations!" +
                                " you're eligible to apply for this POSt!",
                        Toast.LENGTH_SHORT);
                myToast.show();
            }
        });
    }
}