package com.example.mariokarttournamentorganizer;

import static android.net.Uri.fromFile;

import static java.net.Proxy.Type.HTTP;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void processClick(View view) {
        EditText messageView = (EditText) findViewById(R.id.messageTextInput);
        String messageText = messageView.getText().toString();

        if (messageText.isEmpty()) {
            return;
        }

        System.out.println(messageText);
        Uri messageImg = fromFile(new File("/res/raw/testimage.png"));
            // works even with bad pathnames
        if (messageImg == null) {
            System.out.println("image null");
        }
        composeMmsMessage(messageText, messageImg);

    }
    public void composeMmsMessage(String message, Uri attachment){
        System.out.println("composing");
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("smsto:"));  // Only SMS apps respond to this.
        intent.putExtra("sms_body", message);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            System.out.println("starting");
            startActivity(intent);
        } else {
            System.out.println("something wrong");
                // always goes here, never works
        }
    }
}