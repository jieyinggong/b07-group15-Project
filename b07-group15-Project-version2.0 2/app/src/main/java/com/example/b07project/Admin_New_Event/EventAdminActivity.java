package com.example.b07project.Admin_New_Event;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.b07project.R;
import com.example.b07project.dbOperation_Information.CreateItem;
import com.example.b07project.dbOperation_Information.CreateOperation;
import com.example.b07project.dbOperation_Information.DefaultCallback;
import com.example.b07project.main.CheckValidity;
import com.example.b07project.main.Event;
import com.example.b07project.main.DateTime;
import com.example.b07project.main.SetToolbar;

import java.util.Calendar;

public class EventAdminActivity extends AppCompatActivity {
    private EditText startDateTimeEditText;
    private EditText endDateTimeEditText;
    private final Calendar startDateTime = Calendar.getInstance();
    private final Calendar endDateTime = Calendar.getInstance();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_post_event);

        startDateTimeEditText = findViewById(R.id.startDateTimeEditText);
        endDateTimeEditText = findViewById(R.id.endDateTimeEditText);

        startDateTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(startDateTime, startDateTimeEditText);
            }
        });

        endDateTimeEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker(endDateTime, endDateTimeEditText);
            }
        });


        Button submitButton = findViewById(R.id.submit_button);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validateDateTime()){
                createNewEvent();}
            }
        });

        Toolbar toolbar = findViewById(R.id.back_bar);
        SetToolbar.setBackFinish(this, toolbar);
    }

    private void showDatePicker(final Calendar calendar, final EditText editText) {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                calendar.set(Calendar.YEAR, year);
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                showTimePicker(calendar, editText);
            }
        };

        new DatePickerDialog(
                EventAdminActivity.this,
                dateSetListener,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
        ).show();
    }

    private void showTimePicker(final Calendar calendar, final EditText editText) {
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendar.set(Calendar.MINUTE, minute);
                String formattedDateTime = DateTime.formatDateTime(calendar);
                editText.setText(formattedDateTime);
            }
        };

        new TimePickerDialog(
                EventAdminActivity.this,
                timeSetListener,
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE),
                true
        ).show();
    }

    private boolean validateDateTime() {
        if (startDateTime.after(endDateTime)) {
            Toast.makeText(this, "End date and time must be later than the start date and time.", Toast.LENGTH_LONG).show();
            endDateTimeEditText.setText("");
            return false;
        }else{return true;}
    }


    String generateString(int EdittextID) {
        EditText editText = findViewById(EdittextID);
        return editText.getText().toString();
    }

    private void createNewEvent(){
        Event event = new Event();

        String subject = generateString(R.id.SugjectEdit);
        if (CheckValidity.checkShortValidity(subject)){
            event.subject = subject;
        }else{
            showToast("the number of characters of subject must be within 1 to 200");
            return;
        }

        String startDateTime = generateString(R.id.startDateTimeEditText);
        if (CheckValidity.checkShortValidity(startDateTime)) {
            event.startDateTime = startDateTime;
        }else{
            showToast("Start Date and Time cannot be empty！");
            return;
        }

        String endDateTime =  generateString(R.id.endDateTimeEditText);
        if (CheckValidity.checkShortValidity(startDateTime)) {
            event.endDateTime = endDateTime;
        }else{
            showToast("End Date and Time cannot be empty！");
            return;
        }

        String location = generateString(R.id.LocationEdit);
        if (CheckValidity.checkShortValidity(location)){
            event.location = location;
        }else{
            showToast("the number of characters of location must be within 1 to 200");
            return;
        }

        String spcaelimit = generateString(R.id.SpaceLimitEdit);
        if (CheckValidity.checkShortValidity(spcaelimit)){
            try{
                event.CurrentAvailableSpace = Integer.parseInt(spcaelimit);}
            catch(Exception e){
                showToast(e.getMessage());
                return;
            }
        }else{
            showToast("Participants limit cannot be empty!");
            return;
        }


        String content = generateString(R.id.ContentEdit);
        if (CheckValidity.checkLongValidity(content)){
            event.content = content;
        }else{
            showToast("the number of characters of event description must be within 1 to 10000");
            return;
        }

        CreateOperation newItem = new CreateItem();
        newItem.create("Event", event, new DefaultCallback() {
                @Override
                public void onSuccess() {
                    showToast("Success!");
                    finish();
                }

                @Override
                public void onFailure(Exception e) {
                    showToast(e.getMessage());
                }
            });
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

}
