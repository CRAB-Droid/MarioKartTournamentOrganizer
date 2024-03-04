package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class UserACTActivity extends AppCompatActivity {
    private Button addToCalendar;
    private Button joinTournament;
    public static final String PLAYERS_FIELD = "players";
    private FirebaseFirestore myFireStore = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.useractpage);

        //getting username used to login
        Intent loginInfoIntent = getIntent();
        String usernameStr = loginInfoIntent.getStringExtra("Username");

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

            //updating players in firebase
            //which act is being changed is currently hardcoded
            DocumentReference actToBeUpdated = myFireStore.collection("act_objects").document("act5");
            actToBeUpdated.update(PLAYERS_FIELD, FieldValue.arrayUnion(usernameStr));

            Log.v("Join", "Button Clicked");
    });
    }
}
