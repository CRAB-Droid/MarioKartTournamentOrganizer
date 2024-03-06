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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class ViewACTActivity extends AppCompatActivity {
    private TextView actTitleTextView;
    private TextView whoTextView;
    private TextView whenTextView;
    private TextView whereTextView;
    private TextView resultTextViewHeader;
    private TextView resultTextView;
    private Button addToCalendar;
    private Button joinOrResults;
    public static final String PLAYERS_FIELD = "players";
    private static final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final String username = user.getEmail();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_act);

        actTitleTextView = (TextView) findViewById(R.id.actTitleTextViewInfo);
        whoTextView = (TextView) findViewById(R.id.whoTextViewInfo);
        whenTextView = (TextView) findViewById(R.id.whenTextViewInfo);
        whereTextView = (TextView) findViewById(R.id.whereTextViewInfo);
        resultTextViewHeader = (TextView) findViewById(R.id.resultTextViewHeader);
        resultTextView = (TextView) findViewById(R.id.resultTextViewInfo);
        addToCalendar = (Button) findViewById(R.id.addToCalendarButton);
        joinOrResults = (Button) findViewById(R.id.joinOrResultsButton);

        String actTitle = getIntent().getStringExtra("name"); // get name of ACT collection
        HashMap<String, Object> data = (HashMap<String, Object>)getIntent().getSerializableExtra("data"); // get data from ACT collection
        processData(actTitle, data);
    }

    private void processData(String actTitle, Map<String, Object> data) {
        if (data == null) {
            Log.e(TAG, "invalid ACT id");
            finish();
        }
        // Set TextViews with correct data
        actTitleTextView.setText(actTitle);

        String playerString = "";
        ArrayList players = (ArrayList) data.get("players");
        for (int i=0; i<players.size(); i++) {
            playerString = playerString + (i+1) + ". " + players.get(i) + "\n";
        }
        whoTextView.setText(playerString);

        whenTextView.setText(data.get("time").toString() + " " + data.get("date").toString());
        whereTextView.setText(data.get("location").toString());
        Object result = data.get("result");
        if (result != null)
            resultTextView.setText(result.toString());
        else
            resultTextView.setText("null"); // will get hidden with View.GONE anyway


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

        boolean userIsAdmin = Objects.equals(username, (String) data.get("adminID"));
        if (userIsAdmin)
            joinOrResults.setText("Enter Results");
        else // normal user perms, didn't create this particular ACT
            joinOrResults.setText("Join this ACT");

        joinOrResults.setOnClickListener(v -> {
            if (userIsAdmin) {
                enterResults(actTitle);
            } else {
                joinACT(actTitle);
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

    private void enterResults(String actTitle) {
        Log.v("enter results", "Button Clicked");

        Intent enterResults = new Intent(this, EnterResultsActivity.class);
        enterResults.putExtra("actTitle", actTitle);
        enterResults.putExtra("fromPhoto", false);
        startActivity(enterResults);
    }

    private void joinACT(String actTitle) {
        Log.v(TAG, "Button Clicked");
        Log.d(TAG, "actTitle: " + actTitle);
        Log.d(TAG, "user: " + username);

        DocumentReference actDocument = FirebaseFirestore.getInstance()
                .collection("act_objects")
                .document(actTitle);

        actDocument.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                // Document exists, proceed with the update
                DocumentReference thisACT = FirebaseFirestore.getInstance().collection("act_objects").document(actTitle);
                thisACT.update(PLAYERS_FIELD, FieldValue.arrayUnion(username));


            } else {
                // Document does not exist, handle the error or inform the user
                Log.e("Join", "Document does not exist for title: " + actTitle);
                // You might want to show an error message to the user or take appropriate action.
            }
        })
        .addOnFailureListener(e -> {
            // Handle the failure, e.g., log an error message
            Log.e("Join", "Error checking document existence", e);
        });

        // fetch data so it updates users on screen when you sign up
        actDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data");
                        processData(actTitle, document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}