package com.example.mariokarttournamentorganizer;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;

public class CalendarConnectionActivity extends AppCompatActivity{
    EditText title;
    EditText location;
    EditText description;
    EditText emails;
    TextView emailStorage;
    ArrayList<String> emailStorageList;
    int count;
    Button addEmail;
    Button addEvent;
    HashMap<String, Object> hash;
    String ACTName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_connection);

        title = findViewById(R.id.titleEditText);
        location = findViewById(R.id.locationEditText);
        description = findViewById(R.id.descEditText);
        emails = findViewById(R.id.emailEditText);
        emailStorage = findViewById(R.id.emailStorage);
        addEmail = findViewById(R.id.addEmailButton);
        count = 0;
        emailStorageList = new ArrayList<String>();

        hash = (HashMap<String, Object>) getIntent().getSerializableExtra("data");
        ACTName = getIntent().getStringExtra("name");

        addEvent = findViewById(R.id.addEventButton);

        description.setText("Mario Kart ACT, hosted by " + hash.get("adminID") + ".");
        location.setText(hash.get("location").toString());
        title.setText(ACTName);
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!title.getText().toString().isEmpty() && !location.getText().toString().isEmpty() &&
                        !description.getText().toString().isEmpty()){
                    Intent toCalendar = new Intent(Intent.ACTION_INSERT);

                    ComponentName cn = new ComponentName("com.google.android.calendar", "com.android.calendar.LaunchActivity");
                    toCalendar.setComponent(cn);
                    toCalendar.setData(CalendarContract.Events.CONTENT_URI);
                    toCalendar.putExtra(CalendarContract.Events.TITLE, title.getText().toString());
                    toCalendar.putExtra(CalendarContract.Events.DESCRIPTION, description.getText().toString());
                    toCalendar.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
                    toCalendar.putExtra(CalendarContract.Events.ALL_DAY, true);

                    String resultString = TextUtils.join(", ", emailStorageList);

                    toCalendar.putExtra(Intent.EXTRA_EMAIL, resultString);

                    if(toCalendar.resolveActivity(getPackageManager()) != null){
                        //toCalendar.setComponent(cn);
                        startActivity(toCalendar);
                    }
                    else {
                        Toast.makeText(CalendarConnectionActivity.this,
                                "There is no app that can handle this request.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(CalendarConnectionActivity.this,
                            "Please fill out all necessary fields.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(count < 7 ) {
                    if (isValidEmail(emails.getText())) {
                        emailStorageList.add(emails.getText().toString());
                        emailStorage.append(emails.getText() + "\n");
                        count++;
                        emails.setText("");
                    }
                    else {
                        Toast.makeText(CalendarConnectionActivity.this,
                                "Not a valid email.", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(CalendarConnectionActivity.this,
                            "Maximum amount of people to invite.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}