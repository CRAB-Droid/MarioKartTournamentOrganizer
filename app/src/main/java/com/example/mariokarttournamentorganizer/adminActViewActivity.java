package com.example.mariokarttournamentorganizer;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class adminActViewActivity extends AppCompatActivity {

    //Text that shows who is attending the event.
    TextView whoTextView;
    //Text that shows when the event will be.
    TextView whenTextView;
    //Button that will change to screen where results are inputted.
    Button enterResultsButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_act_view);

        whoTextView = findViewById(R.id.whoTextView);
        whenTextView = findViewById(R.id.whenTextView);
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

    /////////////////Switching Screens////////////////
    private void switchToStateXXX(){
        //Intent toNextState = new Intent(this, XXX.class);
        //startActivity(XXX);
    }
    //////////////////////////////////////////////////

}
