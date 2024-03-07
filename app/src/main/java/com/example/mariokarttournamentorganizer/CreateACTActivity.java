package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateACTActivity extends AppCompatActivity {
    private Button createButton;
    private EditText date;
    private String dateStr;
    private EditText time;
    private String timeStr;
    private EditText location;
    private String locationStr;
    public static final String TAG = "creatingACT";
    private ACTObject actObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createact);

        // making ACTObject
        actObj = new ACTObject();

        //getting the username from the homescreen, to use for the adminID and the players list
        Intent loginInfoIntent = getIntent();
        String usernameStr = loginInfoIntent.getStringExtra("Username");
        Log.d(TAG, "username: " + usernameStr);

        //Setup input fields
        date = (EditText) findViewById(R.id.editTextDate);
        time = (EditText) findViewById(R.id.editTextTime);
        location = (EditText) findViewById(R.id.editTextLocation);

        //Set up create button
        createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(v->
        {
            dateStr = date.getText().toString();
            timeStr = time.getText().toString();
            locationStr = location.getText().toString();

            try {
                if(!dateStr.isEmpty() && !timeStr.isEmpty() && !locationStr.isEmpty() && checkDate(dateStr)) {
                    actObj.createACT(usernameStr, dateStr, timeStr, locationStr);
                    finish();
                }
                else {
                    Toast.makeText(CreateACTActivity.this,
                            "Please fill out all necessary fields with valid inputs.", Toast.LENGTH_SHORT).show();
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        });
    }

    //checks to see if the inputted date is valid and in the future
    public static boolean checkDate(String dateString) throws ParseException {
        Date inputtedDate = new SimpleDateFormat("MM/dd/yyyy").parse(dateString);
        return new Date().before(inputtedDate);
    }

}
