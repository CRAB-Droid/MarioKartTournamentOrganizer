package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button loginButton;
    private EditText username;
    private String userNameStr;
    private EditText password;
    private String passWordStr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        //Set up username field to store input string
        username = (EditText) findViewById(R.id.editText);

        //Set up password field to store input string
        password = (EditText) findViewById(R.id.editTextPassword);

        //Set login button to switch screens
        loginButton = (Button) findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(v->
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
//              for(username[i] in database)
//
//                  //Typing in wrong password / trying to create account already made
//                  if (userNameStr == username[i] && passWordStr != username[i].password)
//                      //Throw a toast telling them that user is already an account or that they
//                      //may have typed incorrect password
//
//                  //Correct already made user and password
//                  else if (userNameStr == username[i] && passWordStr == username[i].password)
//                      //Create intent and move to main screen with users account
                        Intent intentUser = new Intent(this, MainActivity.class);
                        intentUser.putExtra("Username", userNameStr);
                        intentUser.putExtra("Password", passWordStr);
                        startActivity(intentUser);

        });


        //Set signup button to switch screens
        signUpButton = (Button) findViewById(R.id.signupbutton);
        signUpButton.setOnClickListener(v->
        {
            Intent intentSignUp = new Intent(this, SignupActivity.class);
            startActivity(intentSignUp);
        });



    }
}
