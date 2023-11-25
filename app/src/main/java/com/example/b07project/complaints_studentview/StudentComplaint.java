package com.example.b07project.complaints_studentview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07project.main.Complaint;
import com.example.b07project.main.Information;
import com.example.b07project.LoginAndSignup.Student_dashboardActivity;
import com.example.b07project.R;
import com.example.b07project.dbOperation_Information.CreateItem;
import com.example.b07project.dbOperation_Information.CreateOperation;
import com.example.b07project.dbOperation_Information.DefaultCallback;
import com.example.b07project.main.CheckValidity;

public class StudentComplaint extends AppCompatActivity {
    String title;
    String content;
    //********************************hengyi li starts**************************************
    String generateTitle(){
        //replace the title (subject) id with the one that corresponds to editText created by Yutong.
        EditText titleName = findViewById(R.id.SugjectEdit);

        //convert the text of title to a string
        title = titleName.getText().toString();

        return title;
    }

    String generateContent(){
        //replace the content (complaint body) id with the one that corresponds to editText created by Yutong.
        EditText complaintContent = findViewById(R.id.ContentEdit);

        //convert the text of content to a string
        content = complaintContent.getText().toString();

        return content;
    }


    //when SUBMIT button is clicked, the above code should run:
    //see code in studentComplaintInteractive

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_complaints_fragment);

        //replace the id of the button with the corresponding button id created by yutong.
        Button submitButton = findViewById(R.id.submit_button);

        // Set an OnClickListener for the button
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Perform the desired action when the button is clicked
                generateTitleAndContent();
            }
        });

        findViewById(R.id.back_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //back to dashboard
                Intent intent = new Intent(StudentComplaint.this, Student_dashboardActivity.class);
                startActivity(intent);
            }
        });
    }

    // Method to be performed when the button is clicked
    private void generateTitleAndContent() {
        if ((CheckValidity.checkTitleValidity(generateTitle())) && (CheckValidity.checkContentValidity(generateContent()))) {
            CreateOperation newItem = new CreateItem();
            Information complaint = new Complaint(title, content);
            newItem.create("Complaint", complaint, new DefaultCallback() {
                @Override
                public void onSuccess() {
                    showToast("Submit Success!");
                    finish();
                }

                @Override
                public void onFailure(Exception e) {
                    showToast(e.getMessage());
                }
            });
            //save title and content information to the database
            //***************please add database methods here*************
        } else if (!CheckValidity.checkTitleValidity(generateTitle())) {
            showToast ("the number of characters of the title must be within 1 to 100");
        } else if (!CheckValidity.checkContentValidity(generateContent())) {
            showToast ("the number of characters of the content must be within 1 to 5000");
        }
    }

    // Toast a warning message if either title or content violates the respective range
    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


    //********************************hengyi li ends****************************************
}

