package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private Button signUpButton;
    private Button loginButton;
    private EditText username;
    private String userNameStr;
    private EditText password;
    private String passWordStr;

    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen);

        //Getting instance of firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();

        //Set up username field to store input string
        username = (EditText) findViewById(R.id.editText);

        //Set up password field to store input string
        password = (EditText) findViewById(R.id.editTextPassword);

        //Set login button to switch screens
        loginButton = (Button) findViewById(R.id.loginbutton);
        loginButton.setOnClickListener(v->
        {
            loginUser(); });


        //Set signup button to switch screens
        signUpButton = (Button) findViewById(R.id.signupbutton);
        signUpButton.setOnClickListener(v->
        {
            Intent intentSignUp = new Intent(this, SignupActivity.class);
            startActivity(intentSignUp);
        });



    }

    private void loginUser(){
        //Retrieving the username and password to send to check
        userNameStr = username.getText().toString();
        passWordStr = password.getText().toString();
        if (!userNameStr.isEmpty() && !passWordStr.isEmpty()) {
            firebaseAuth.signInWithEmailAndPassword(userNameStr, passWordStr)
                    .addOnCompleteListener(
                            new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(
                                        @NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(getApplicationContext(), "Login successful!!", Toast.LENGTH_LONG).show();

                                        //Sign-in is successful
                                        Intent homescreen = new Intent(LoginActivity.this, homeScreenActivity.class);
                                        startActivity(homescreen);
                                    } else {

                                        //Sign-in failed
                                        Toast.makeText(getApplicationContext(), "Incorrect email or password", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
        }
        else {
            Toast.makeText(LoginActivity.this,
                    "Please fill out all necessary fields.", Toast.LENGTH_SHORT).show();
        }

    }

}
