package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.AuthResult;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignupActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText username;
    private String userNameStr;
    private EditText password;
    private String passWordStr;
    private EditText RetypedPass;
    private String RetypedPassStr;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_screen);

        //Getting instance of firebase authentication
        firebaseAuth = FirebaseAuth.getInstance();

        //Set up username field to store input string
        username = (EditText) findViewById(R.id.editText);

        //Set up password field to store input string
        password = (EditText) findViewById(R.id.editTextPassword);
        RetypedPass = (EditText) findViewById(R.id.editTextReTypePassword);

        //Set login button to switch screens
        loginButton = (Button) findViewById(R.id.loginbuttonNEW);
        loginButton.setOnClickListener(v ->
        {
            registerUser(); });
    }

    private void registerUser(){
        //Retrieving the username and password to send to check
        userNameStr = username.getText().toString();
        passWordStr = password.getText().toString();
        RetypedPassStr = RetypedPass.getText().toString();
        if (!userNameStr.isEmpty() && !passWordStr.isEmpty() && !RetypedPassStr.isEmpty() && passWordStr.equals(RetypedPassStr)) {
//            Log.v("Selected username", userNameStr);
//            Log.v("Selected password", passWordStr);
            if (!isStrongPassword(passWordStr)) {
                //Password must be at least 8 characters long and contain a Upper case, lower case, number and special char
                Toast.makeText(getApplicationContext(), "Pass must be 8 chars + contain upper+lower case," + "number, special char"
                        , Toast.LENGTH_LONG).show();
            } else {
                //Signup new user
                firebaseAuth.createUserWithEmailAndPassword(userNameStr, passWordStr)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(),
                                                    "Signup successful!",
                                                    Toast.LENGTH_LONG)
                                            .show();

                                    Intent homescreen = new Intent(SignupActivity.this, homeScreenActivity.class);
//                                intentUser.putExtra("Username", userNameStr);
//                                intentUser.putExtra("Password", passWordStr);
                                    startActivity(homescreen);
                                } else {

                                    //Signup failed
                                    Toast.makeText(
                                                    getApplicationContext(),
                                                    "User is already an account" +
                                                            "or Email is invalid",
                                                    Toast.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });
            }
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
    }

    //Helper function to check if password is strong
    public static boolean isStrongPassword(String password) {
            // Define a regular expression to match uppercase, lowercase, digit, and special character
            String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!])(?=.*[a-zA-Z0-9@#$%^&+=!]).{8,}$";

            // Create a Pattern object
            Pattern pattern = Pattern.compile(regex);

            // Create a Matcher object
            Matcher matcher = pattern.matcher(password);

            // Return true if the password matches the pattern, otherwise false
            return matcher.matches();
        }
}
