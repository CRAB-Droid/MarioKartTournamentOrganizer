package com.example.mariokarttournamentorganizer;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;


public class ViewACTActivity extends AppCompatActivity {
    private TextView whoTextView;
    private TextView whenTextView;
    private TextView whereTextView;
    private TextView resultTextViewHeader;
    private TextView resultTextView;
    private Button addToCalendar;
    private Button joinOrResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_act);

        whoTextView = (TextView) findViewById(R.id.whoTextViewInfo);
        whenTextView = (TextView) findViewById(R.id.whenTextViewInfo);
        whereTextView = (TextView) findViewById(R.id.whereTextViewInfo);
        resultTextViewHeader = (TextView) findViewById(R.id.resultTextViewHeader);
        resultTextView = (TextView) findViewById(R.id.resultTextViewInfo);
        addToCalendar = (Button) findViewById(R.id.addToCalendarButton);
        joinOrResults = (Button) findViewById(R.id.joinOrResultsButton);

        String actTitle = getIntent().getStringExtra("name"); // get name of ACT collection
        HashMap<String, Object> data = (HashMap<String, Object>)getIntent().getSerializableExtra("data"); // get data from ACT collection
        if (data == null) {
            Log.e(TAG, "invalid ACT id");
            finish();
        }

        // Set TextViews with correct data
        String playerString = "";
        ArrayList players = (ArrayList) data.get("players");
        for (int i=0; i<players.size(); i++) {
            System.out.println(players.get(i));
            playerString = playerString + (i+1) + ". " + players.get(i) + "\n";
        }
        whoTextView.setText(playerString);
        whenTextView.setText(data.get("time").toString() + " " + data.get("date").toString());
        whereTextView.setText(data.get("location").toString());

        // check if ACT is complete or not based on boolean field in firebase
        if ((boolean) data.get("completed")) {
            addToCalendar.setVisibility(View.GONE); // GONE = invisible and disabled
            joinOrResults.setVisibility(View.GONE);
            return;
        }
        // else, ACT is still upcoming:
        resultTextViewHeader.setVisibility(View.GONE);
        resultTextView.setVisibility(View.GONE);

        addToCalendar.setOnClickListener(v -> addToCalendar());

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        boolean userIsAdmin = Objects.equals(user.getEmail(), (String) data.get("adminID"));
        System.out.println(user.getEmail()); // delete
        System.out.println(data.get("adminID")); // delete

        if (userIsAdmin)
            joinOrResults.setText("Enter Results");
        else // normal user perms, didn't create this particular ACT
            joinOrResults.setText("Join this ACT");

        joinOrResults.setOnClickListener(v -> {
            if (userIsAdmin) {
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