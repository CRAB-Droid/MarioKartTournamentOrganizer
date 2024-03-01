package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
                passWordStr = password.getText().toString();
                RetypedPassStr = RetypedPass.getText().toString();
            if (!userNameStr.isEmpty() && !passWordStr.isEmpty() && !RetypedPassStr.isEmpty() && passWordStr.equals(RetypedPassStr)) {
                Log.v("Selected username", userNameStr);
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
            }
            else if(!passWordStr.isEmpty() && RetypedPassStr.isEmpty()) {
                Toast.makeText(SignupActivity.this,
                        "Please retype password.", Toast.LENGTH_SHORT).show();
            }
            else if(!passWordStr.equals(RetypedPassStr)){
                Toast.makeText(SignupActivity.this,
                        "Passwords must match.", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(SignupActivity.this,
                        "Please fill out all necessary fields.", Toast.LENGTH_SHORT).show();
            }

        });
    }
}
