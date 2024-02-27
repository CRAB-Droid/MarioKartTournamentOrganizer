package com.example.mariokarttournamentorganizer;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        //Set login button to switch screens
        loginButton = (Button) findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(v->
        {
//            Add in a switch to main screen, prob intent to send username to next state as well
//            setContentView(R.layout.);
        });


        //Set signup button to switch screens
        signUpButton = (Button) findViewById(R.id.signupbutton);
        signUpButton.setOnClickListener(v->
        {
            setContentView(R.layout.signup_screen);
        });

    }
}
