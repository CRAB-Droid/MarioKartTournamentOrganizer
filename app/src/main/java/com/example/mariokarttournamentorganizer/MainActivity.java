package com.example.mariokarttournamentorganizer;

import static android.net.Uri.fromFile;

import static java.net.Proxy.Type.HTTP;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = (Button) findViewById(R.id.sendMessageButton);

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.SEND_SMS}, 100);

        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED)
                    sendSMS();
                else {
                    System.out.println("not permitted");

                }
            }
        });
    }

    public void sendSMS() {
        String message = ((EditText)findViewById(R.id.messageTextInput)).getText().toString();
        String number = ((EditText)findViewById(R.id.phoneNumberInput)).getText().toString();

        System.out.println(number + ":  " + message);

        if (message.isEmpty() || number.isEmpty()) {
            return;
        }

        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(number, null, message, null, null);
    }
//    public void composeMmsMessage(String message, Uri attachment){
//        System.out.println("composing");
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("smsto:"));  // Only SMS apps respond to this.
//        intent.putExtra("sms_body", message);
//        intent.putExtra(Intent.EXTRA_STREAM, attachment);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            System.out.println("starting");
//            startActivity(intent);
//        } else {
//            System.out.println("something wrong");
//                // always goes here, never works
//        }
//    }
}