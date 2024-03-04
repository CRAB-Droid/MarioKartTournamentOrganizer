package com.example.mariokarttournamentorganizer;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ViewACTActivity extends AppCompatActivity {
    private TextView whoTextView;
    private TextView whenTextView;
    private TextView whereTextView;
    private TextView resultTextViewHeader;
    private TextView resultTextView;
    private Button addToCalendar;
    private Button joinOrResults;
    private boolean admin = false; // HARDCODED, just for testing. Fetch based on auth from firebase
    private boolean complete = false; // HARDCODED, just for testing. Fetch based on ACT ID from firebase
    private int ACTID = 7; // HARDCODED. Get from intent that launches page
    // hardcoded the below to 7 as well
    FirebaseFirestore db;
    private DocumentReference docRef;
//    private DocumentSnapshot actDocSnap;
    private Map<String, Object> data;

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

        db = FirebaseFirestore.getInstance();
        // hard code 7 for now
        docRef = db.collection("act_objects").document("7");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        data = document.getData();
                        Log.d(TAG, "DocumentSnapshot data: " + data);
                        processData();
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void processData() {
        if (data == null) {
            Log.e(TAG, "invalid ACT id");
            finish();
        }

        String playerString = "";
        ArrayList players = (ArrayList) data.get("Players");
        for (int i=0; i<8; i++) {
            System.out.println(players.get(i));
            playerString = playerString + (i+1) + ". " + players.get(i) + "\n";
        }
        whoTextView.setText(playerString);

        whenTextView.setText(data.get("Date/Time").toString());
        whereTextView.setText(data.get("location").toString());


        // check if ACT is complete or not based on boolean field in firebase
        if (complete) {
            addToCalendar.setVisibility(View.GONE); // GONE = invisible and disabled
            joinOrResults.setVisibility(View.GONE);
            return;
        }

        // ACT is still upcoming:

        resultTextViewHeader.setVisibility(View.GONE);
        resultTextView.setVisibility(View.GONE);

        addToCalendar.setOnClickListener(v -> addToCalendar());

        // check to see if the user is an admin for this ACT (if they created if or not)
        // for now, admin is hardcoded when assigned, for testing
        if (admin)
            joinOrResults.setText("Enter Results");
        else // normal user perms, didn't create this particular ACT
            joinOrResults.setText("Join this ACT");

        joinOrResults.setOnClickListener(v -> {
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