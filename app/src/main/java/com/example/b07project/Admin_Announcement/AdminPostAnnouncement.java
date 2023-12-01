package com.example.b07project.Admin_Announcement;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.b07project.Dashboard.Admin_dashboardActivity;
import com.example.b07project.R;
import com.example.b07project.dbOperation_Information.CreateItem;
import com.example.b07project.dbOperation_Information.CreateOperation;
import com.example.b07project.dbOperation_Information.DefaultCallback;
import com.example.b07project.main.Annoucement;
import com.example.b07project.main.CheckValidity;
import com.example.b07project.main.Information;
import com.example.b07project.main.SetToolbar;

public class AdminPostAnnouncement extends AppCompatActivity {
    String announcement_title;
    String announcement_content;

    /**
     * it returns the title as a string of the announcement title entered by admin
     */
    String generateAnnouncementTitle(){
        EditText AnnouncementTitleName = findViewById(R.id.announcement_title);

        announcement_title = AnnouncementTitleName.getText().toString();

        return announcement_title;
    }

    /**
     * it returns the content as a string of the announcement content entered by admin
     */
    String generateAnnouncementContent(){
        EditText AnnouncementContent = findViewById(R.id.announcement_content);

        announcement_content = AnnouncementContent.getText().toString();

        return announcement_content;
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void generateTitleAndContentForAnnouncement(){
        if((CheckValidity.checkShortValidity(generateAnnouncementTitle())) && (CheckValidity.checkLongValidity(generateAnnouncementContent()))) {
            CreateOperation newItem = new CreateItem();
            Information announcement = new Annoucement(announcement_title, announcement_content);
            newItem.create("Announcement", announcement, new DefaultCallback() {
                @Override
                public void onSuccess() {
                    showToast("Successfully posted!");
                    finish();
                }

                @Override
                public void onFailure(Exception e) {
                    showToast(e.getMessage());
                }
            });
        } else if (!CheckValidity.checkShortValidity(generateAnnouncementTitle())) {
            showToast ("The number of characters of the title must be within 1 to 100. Please try again");
        } else if (!CheckValidity.checkLongValidity(generateAnnouncementContent())) {
            showToast(("The number of characters of the content must be within 1 to 5000. Please try again"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_post_announcement_fragment);

        Button postButton = findViewById(R.id.announcement_post_button);

        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);

        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateTitleAndContentForAnnouncement();
            }
        });



        //back button
        findViewById(R.id.back_bar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //go back to dashboard
                Intent intent = new Intent(AdminPostAnnouncement.this, Admin_dashboardActivity.class);
                startActivity(intent);
            }
        });
    }



}
