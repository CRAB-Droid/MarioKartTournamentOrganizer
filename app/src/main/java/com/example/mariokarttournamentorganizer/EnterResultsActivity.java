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

