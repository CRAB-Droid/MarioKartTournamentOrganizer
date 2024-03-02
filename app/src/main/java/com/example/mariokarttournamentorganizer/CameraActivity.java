package com.example.mariokarttournamentorganizer;

import static android.content.Intent.ACTION_SEND;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.ContentValues;
import android.os.Bundle;
import android.provider.MediaStore;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class CameraActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        imageView = findViewById(R.id.myImage);
//        button = findViewById(R.id.myButton);

        // Set up photo location in gallery
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "jersey_swap");
        values.put(MediaStore.Images.Media.DESCRIPTION, "winners_of_ACT");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Taking Picture and saving it to post
        takeAndSavePhoto();

//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                takeAndSavePhoto();
//            }
//        });

    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // Photo taken and saved, time to post!
                    postToInstagram();
                } else {
                    Log.e("MainActivity", "Activity Result NOT OK");
                }
            });

    protected void takeAndSavePhoto() {
        Bundle resultOptions = new Bundle();
        Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        // Take and save photo
        activityResultLauncher.launch(openCamera);
    }

    void postToInstagram() {
        Intent share = new Intent(ACTION_SEND);
        share.setAction(ACTION_SEND);
        share.setType("image/*");
        share.putExtra("source_application", "@string/facebook_app_id");
        share.putExtra(Intent.EXTRA_STREAM, imageUri);

        try {
            startActivity(Intent.createChooser(share, "Share to"));
        } catch (ActivityNotFoundException e) {
            Log.e("MainActivity", "ActivityNotFoundException");
        }
    }
}