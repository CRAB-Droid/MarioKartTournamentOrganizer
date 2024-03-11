package com.example.mariokarttournamentorganizer;

import android.app.Activity;
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

        // Set up photo location in gallery
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "jersey_swap");
        values.put(MediaStore.Images.Media.DESCRIPTION, "winners_of_ACT");
        imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        //Taking Picture and saving it to post
        takeAndSavePhoto();

    }
    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    // Photo taken and saved, time to post!

                    //switch back to results screen
                    Intent backToResults = new Intent(this, EnterResultsActivity.class);
                    backToResults.setData(imageUri);
                    setResult(Activity.RESULT_OK, backToResults);
                    finish();
                }
                else {
                    Log.e("ActivityResultLauncher", "Activity Result NOT OK");
                }
            });

    protected void takeAndSavePhoto() {
        Bundle resultOptions = new Bundle();
        Intent openCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        openCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);

        // Take and save photo
        activityResultLauncher.launch(openCamera);
    }

}