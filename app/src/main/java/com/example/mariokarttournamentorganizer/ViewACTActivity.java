package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ViewACTActivity extends AppCompatActivity {
    private TextView whoTextView;
    private TextView whenTextView;
    private TextView whereTextView;
    private Button addToCalendar;
    private Button joinOrResults;
    private boolean admin = false; // HARDCODED, just for testing. Fetch based on auth from firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_act);

        whoTextView = (TextView) findViewById(R.id.whoTextViewInfo);
        whenTextView = (TextView) findViewById(R.id.whenTextViewInfo);
        whereTextView = (TextView) findViewById(R.id.whereTextViewInfo);
        addToCalendar = (Button) findViewById(R.id.addToCalendarButton);
        joinOrResults = (Button) findViewById(R.id.joinOrResultsButton);

        addToCalendar.setOnClickListener(v-> addToCalendar());

        // check to see if the user is an admin for this ACT (if they created if or not)
        // for now, admin is hardcoded when assigned, for testing
        if (admin)
            joinOrResults.setText("Enter Results");
        else // normal user perms, didn't create this particular ACT
            joinOrResults.setText("Join this ACT");

        joinOrResults.setOnClickListener(v-> {
            if (admin) {
                enterResults();
            } else {
                joinACT();
            }
        });
    }

    private void addToCalendar() {
        //TODO
        //Change names to actual activities
        //Intent addCalendar = new Intent(this, Calendar.class);
        //startActivity(addCalendar);
        Log.v("Add Calendar", "Button Clicked");
    }

    private void enterResults() {
        Intent enterResults = new Intent(this, EnterResultsActivity.class);
        startActivity(enterResults);
    }

    private void joinACT() {
        //TODO
        //Change names to actual activities
        //Intent join = new Intent(this, Join.class);
        //startActivity(join);
        Log.v("Join", "Button Clicked");
    }
}
