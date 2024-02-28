package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText username;
    private String userNameStr;
    private EditText password;
    private String passWordStr;
    private EditText RetypedPass;
    private String RetypedPassStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        //Set up username field to store input string
        username = (EditText) findViewById(R.id.editText);

        //Set up password field to store input string
        password = (EditText) findViewById(R.id.editTextPassword);
        RetypedPass = (EditText) findViewById(R.id.editTextReTypePassword);

        //Set login button to switch screens
        loginButton = (Button) findViewById(R.id.loginbuttonNEW);
        loginButton.setOnClickListener(v ->
        {
            //Retrieving the username and password to send to check
            userNameStr = username.getText().toString();
            Log.v("Selected username", userNameStr);
            passWordStr = password.getText().toString();
            Log.v("Selected password", passWordStr);

            //Need to implement a username and password check with data base
            //Needs to also check on signup page if username is already in database
            //TODO
            //Logic:
            //Trying to create account already made
//                  if (userNameStr == username[i])
//                      //Throw a toast telling them that user is already an account
//
//                  //New account
//                   else
//                      //First store username and password into database
                      //Create intent and move to main screen with users account
                        Intent intentNewUser = new Intent(this, MainActivity.class);
                        intentNewUser.putExtra("Username", userNameStr);
                        intentNewUser.putExtra("Password", passWordStr);
                        startActivity(intentNewUser);
        });
    }
}
