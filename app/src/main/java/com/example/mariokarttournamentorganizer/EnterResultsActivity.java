package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class EnterResultsActivity extends AppCompatActivity {

    private EditText results;

    private Button submit;

    private Button photo;

    private String resultsStr;

    //for firebase
    public static final String COMPLETED_FIELD= "completed";
    public static final String RESULT_FIELD = "result";
    public static final String TAG = "creatingACT";

    private FirebaseFirestore myFireStore = FirebaseFirestore.getInstance();

    private int buttonClickCounter = 0;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_results);

        //get ACT title from intent
        String actTitle = getIntent().getStringExtra("actTitle");

        results = (EditText) findViewById(R.id.resultsInputEditTextNumber);

        //Set up submit button
        submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(v ->
        {
            resultsStr = results.getText().toString();
            if (buttonClickCounter == 0 && !resultsStr.isEmpty()){
                Toast.makeText(EnterResultsActivity.this,
                        "Click 'Take Photo' button if you would like a winning team picture.", Toast.LENGTH_LONG).show();
                buttonClickCounter++;
            }
            else {
                if (!resultsStr.isEmpty()) {
                    //updating results and completed fielf of chosen act
                    //currently the act to be edited is hardcoded
                    DocumentReference actToBeUpdated = myFireStore.collection("act_objects").document(actTitle);
                    actToBeUpdated.update(RESULT_FIELD, resultsStr);
                    actToBeUpdated.update(COMPLETED_FIELD, true);
                    Toast.makeText(EnterResultsActivity.this,
                            "Result successfully entered.", Toast.LENGTH_SHORT).show();

                    //Go back to homescreen after entering result
//                    Intent homescreen = new Intent(this, homeScreenActivity.class);
//                    startActivity(homescreen);
                    finish();
                } else {
                    Toast.makeText(EnterResultsActivity.this,
                            "Please fill out necessary field.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        photo = (Button) findViewById(R.id.photoButton);
        photo.setOnClickListener(v->
        {
            buttonClickCounter++;
            Intent camera = new Intent(this, CameraActivity.class);
            startActivity(camera);
        });
    }
}

