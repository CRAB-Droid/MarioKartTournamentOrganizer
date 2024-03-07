package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


public class EnterResultsActivity extends AppCompatActivity {

    private EditText results;

    private Button submit;

    private Button photo;

    private String resultsStr;

    private int buttonClickCounter = 0;
    private ACTObject actObj;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_results);

        actObj = new ACTObject();

        //get ACT title from intent
        String actTitle = getIntent().getStringExtra("actTitle");

        results = (EditText) findViewById(R.id.resultsInputEditTextNumber);

        //Set up the submit button
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
                    actObj.enterResult(actTitle, resultsStr);
                    Toast.makeText(EnterResultsActivity.this,
                            "Result successfully entered.", Toast.LENGTH_SHORT).show();

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