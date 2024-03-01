package com.example.mariokarttournamentorganizer;

import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;


public class EnterResults extends AppCompatActivity {

    private EditText results;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_results);

        results = (EditText) findViewById(R.id.resultsInputEditTextNumber);
    }
}
