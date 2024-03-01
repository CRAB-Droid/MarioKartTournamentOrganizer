package com.example.mariokarttournamentorganizer;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class CalendarConnectionActivity extends AppCompatActivity {

    EditText title;
    EditText date;
    EditText location;
    EditText description;

    Button addEvent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_connection);

        title = findViewById(R.id.titleEditText);
        date = findViewById(R.id.dateEditText);
        location = findViewById(R.id.locationEditText);
        description = findViewById(R.id.descEditText);
        addEvent = findViewById(R.id.addEventButton);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty() &&
                        !description.getText().toString().isEmpty()){
                    //!date.getText().toString().isEmpty()
                    Intent toCalendar = new Intent(Intent.ACTION_INSERT);

                    ComponentName cn = new ComponentName("com.google.android.calendar", "com.android.calendar.LaunchActivity");
                    toCalendar.setComponent(cn);
                    toCalendar.setData(CalendarContract.Events.CONTENT_URI);
                    toCalendar.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                    toCalendar.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());
                    toCalendar.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
                    toCalendar.putExtra(CalendarContract.Events.ALL_DAY, true);

                    toCalendar.putExtra(Intent.EXTRA_EMAIL, "tester@gmail.com");

                    if(toCalendar.resolveActivity(getPackageManager()) != null){
                        //toCalendar.setComponent(cn);
                        startActivity(toCalendar);
                    }
                    else {
                        Toast.makeText(CalendarConnectionActivity.this,
                                "There is no app that can handle this request.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(CalendarConnectionActivity.this,
                            "Please fill out all necessary fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}