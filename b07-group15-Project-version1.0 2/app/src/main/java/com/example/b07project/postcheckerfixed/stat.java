package com.example.b07project.postcheckerfixed;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import com.example.b07project.R;
public class stat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat);

        findViewById(R.id.statSpecialButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stat.this, statML.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.statMajorButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stat.this, statMajor.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.statMinorButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(stat.this, CMSpostMain.class);
                startActivity(intent);

                Toast myToast = Toast.makeText(stat.this, "Minor in Statistics is an unlimited program," +
                                "you are automatically qualified",
                        Toast.LENGTH_SHORT);
                myToast.show();



            }
        });



    }
}