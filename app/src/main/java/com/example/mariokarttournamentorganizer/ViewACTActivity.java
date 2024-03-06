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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

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

    public static final String ID_FIELD = "ID";
    public static final String ADMIN_FIELD = "adminID";
    public static final String DATE_FIELD = "date";
    public static final String TIME_FIELD = "time";
    public static final String LOCATION_FIELD = "location";
    public static final String PLAYERS_FIELD = "players";
    public static final String COMPLETED_FIELD = "completed";
    public static final String RESULT_FIELD = "result";
    public static final String TIMESTAMP_FIELD = "Date/Time";

    private String actTitle; // get name of ACT collection
    private DocumentReference actDocument;
    private static final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private static final String username = user.getEmail();


    @Override
    protected void onStart() {
        super.onStart();
        actTitle = getIntent().getStringExtra("name");
        actDocument = FirebaseFirestore.getInstance()
                .collection("act_objects")
                .document(actTitle);
        actDocument.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                // if statement and code under it same as when fetching with button
                if (documentSnapshot.exists()) {
                    processData(documentSnapshot.getData());
                } else if (e != null) {
                    Log.w("ViewACTActivity", "Got an exception!", e);
                }
            }
        });
    }


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

//
//        HashMap<String, Object> data = (HashMap<String, Object>)getIntent().getSerializableExtra("data"); // get data from ACT collection
//        processData(data);
    }

    private void processData(Map<String, Object> data) {
        if (data == null) {
            Log.e("ViewACTActivity", "invalid ACT id");
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



        addToCalendar.setOnClickListener(v -> addToCalendar(data, actTitle));



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

    private void addToCalendar(Map<String, Object> data, String actName) {
        Log.v("Add aj Calendar", "Button Clicked");

        Intent toCalendar = new Intent(this, CalendarConnectionActivity.class);
        toCalendar.putExtra("data", (HashMap<String, Object>) data);
        toCalendar.putExtra("name", actName);
        startActivity(toCalendar);
    }

    private void enterResults(String actTitle) {
        Log.v("enter results", "Button Clicked");

        Intent enterResults = new Intent(this, EnterResultsActivity.class);
        enterResults.putExtra("actTitle", actTitle);
        startActivity(enterResults);
    }

    private void joinACT(String actTitle) {
        Log.v(TAG, "Button Clicked");
        Log.d(TAG, "actTitle: " + actTitle);
        Log.d(TAG, "user: " + username);
        actDocument.update(PLAYERS_FIELD, FieldValue.arrayUnion(username));

//        // fetch data so it updates users on screen when you sign up
//        actDocument.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//            @Override
//            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                if (task.isSuccessful()) {
//                    DocumentSnapshot document = task.getResult();
//                    if (document.exists()) {
//                        Log.d(TAG, "DocumentSnapshot data");
//                        processData(document.getData());
//                    } else {
//                        Log.d(TAG, "No such document");
//                    }
//                } else {
//                    Log.d(TAG, "get failed with ", task.getException());
//                }
//            }
//        });
    }
}