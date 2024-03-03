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

    private FirebaseFirestore mDocRef = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_results);

        results = (EditText) findViewById(R.id.resultsInputEditTextNumber);

        //Set up submit button
        submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(v ->
        {
            resultsStr = results.getText().toString();
            if (!resultsStr.isEmpty()) {
                //TODO
                //still need to actually get the right act  to edit

                Map<String, Object> newInfo = new HashMap<>();
                newInfo.put(COMPLETED_FIELD, true);
                newInfo.put(RESULT_FIELD, resultsStr);



                mDocRef.collection("act_objects").document("act1")
                        .update(newInfo)
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
            } else {
                Toast.makeText(EnterResultsActivity.this,
                        "Please fill out necessary field.", Toast.LENGTH_SHORT).show();
            }
        });

        photo = (Button) findViewById(R.id.photoButton);
        photo.setOnClickListener(v->
        {
            //TODO
            Intent camera = new Intent(this, CameraActivity.class);
            startActivity(camera);
        });
    }
}

