package com.example.mariokarttournamentorganizer;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserACTActivity extends AppCompatActivity {
    private Button addToCalendar;
    private Button joinTournament;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useractpage);

        addToCalendar = (Button) findViewById(R.id.addToCalendarButton);
        addToCalendar.setOnClickListener(v->
        {
            //TODO
            //Change names to actual activities
            //Intent addCalendar = new Intent(this, Calendar.class);
            //startActivity(addCalendar);
            Log.v("Add Calendar", "Button Clicked");
        });

        joinTournament = (Button) findViewById(R.id.joinButton);
        joinTournament.setOnClickListener(v->
        {
            //TODO
            //Change names to actual activities
            //Intent join = new Intent(this, Join.class);
            //startActivity(join);
            Log.v("Join", "Button Clicked");
        });
    }

}
