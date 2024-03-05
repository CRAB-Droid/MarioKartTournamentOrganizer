package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.EventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CreateACTActivity extends AppCompatActivity {
    private Button createButton;
    private EditText date;
    private String dateStr;
    private EditText time;
    private String timeStr;
    private EditText location;
    private String locationStr;

    //for firebase
    public static final String ID_FIELD = "ID";
    public static final String ADMIN_FIELD = "adminID";
    public static final String DATE_FIELD = "date";
    public static final String TIME_FIELD = "time";
    public static final String LOCATION_FIELD = "location";
    public static final String PLAYERS_FIELD = "players";
    public static final String TAG = "creatingACT";
    public static final String COMPLETED_FIELD = "completed";
    public static final String RESULT_FIELD = "result";
    public static final String TIMESTAMP_FIELD = "Date/Time";
    private long count;
    private String docPath;

    private FirebaseFirestore myFireStore = FirebaseFirestore.getInstance();


    @Override
    protected void

    onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createact);

        //getting the username from the homescreen, to use for the adminID and the players list
        Intent loginInfoIntent = getIntent();
        String usernameStr = loginInfoIntent.getStringExtra("Username");
        Log.d(TAG, "username: " + usernameStr);

        //Setup input fields
        date = (EditText) findViewById(R.id.editTextDate);
        time = (EditText) findViewById(R.id.editTextTime);
        location = (EditText) findViewById(R.id.editTextLocation);

        //getting count of act_objects to generate new ID
        Query query = myFireStore.collection("act_objects");
        AggregateQuery countQuery = query.count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    count = snapshot.getCount();
                    docPath = "act" + count;
                    Log.d(TAG, "Count: " + count);
                    Log.d(TAG, "Document name:" + docPath);
                } else {
                    Log.d(TAG, "Count failed: ", task.getException());
                }
            }
        });


        //Set up create button
        createButton = (Button) findViewById(R.id.createButton);
        createButton.setOnClickListener(v->
        {
            dateStr = date.getText().toString();
            timeStr = time.getText().toString();
            locationStr = location.getText().toString();

            try {
                if(!dateStr.isEmpty() && !timeStr.isEmpty() && !locationStr.isEmpty() && checkDate(dateStr)) {

                    //creating a new document in firebase
                    Map<String, Object> act1 = new HashMap<>();
                    act1.put(ID_FIELD, count);
                    act1.put(ADMIN_FIELD, usernameStr);
                    act1.put(TIMESTAMP_FIELD, Timestamp.now());
                    act1.put(COMPLETED_FIELD, false); //default
                    act1.put(DATE_FIELD, dateStr);
                    act1.put(TIME_FIELD, timeStr);
                    act1.put(LOCATION_FIELD, locationStr);
                    act1.put(PLAYERS_FIELD, Arrays.asList(usernameStr));
                    act1.put(RESULT_FIELD, null);

                    myFireStore.collection("act_objects").document(docPath)
                            .set(act1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot successfully written!");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });

                    //Go back to home screen after created
                    Intent homeScreen = new Intent(this, homeScreenActivity.class);
                    startActivity(homeScreen);
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
