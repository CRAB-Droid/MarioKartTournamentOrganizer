package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class CreateACTActivity extends AppCompatActivity {
    private Button createButton;
    private EditText date;
    private String dateStr;
    private EditText time;
    private String timeStr;
    private EditText location;
    private String locationStr;
    @Override
    protected void

    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createact);

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
            if(!dateStr.isEmpty() && !timeStr.isEmpty() && !locationStr.isEmpty()) {
                //TODO Setup button to store input activity into database
                //Maybe also add checks or change input type for time to be valid times (1-12 hrs, 0-59 min)??

                //Go back to home screen after created
                Intent homeScreen = new Intent(this, homeScreenActivity.class);
                startActivity(homeScreen);
            }
            else {
                Toast.makeText(CreateACTActivity.this,
                        "Please fill out all necessary fields.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
