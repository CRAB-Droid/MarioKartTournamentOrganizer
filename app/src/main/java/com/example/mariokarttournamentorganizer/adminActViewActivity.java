package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

public class adminActViewActivity extends AppCompatActivity {

    //Text that shows who is attending the event.
    TextView whoTextView;
    //Text that shows when the event will be.
    TextView whenTextView;
    //Text that shows where the event will be
    TextView whereTextView;
    //Button that will change to screen where results are inputted.
    Button enterResultsButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_act_view);

        whoTextView = findViewById(R.id.whoTextViewInfo);
        whenTextView = findViewById(R.id.whenTextViewInfo);
        whereTextView = findViewById(R.id.whereTextViewInfo);
        enterResultsButton = findViewById(R.id.enterResultButton);

        enterResultsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToStateXXX();
            }
        });

    }

    private void setWhoTextView(String string){
        whoTextView.setText(string);
    }

    private void setWhenTextView(String string){
        whenTextView.setText(string);
    }

    private void setWhereTextView(String string){
        whereTextView.setText(string);
    }

    /////////////////Switching Screens////////////////
    private void switchToStateXXX(){
        Intent enterResults = new Intent(this, EnterResultsActivity.class);
        startActivity(enterResults);
    }
    //////////////////////////////////////////////////

}
