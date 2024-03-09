package com.example.mariokarttournamentorganizer;

import static android.content.Intent.ACTION_SEND;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;

import android.os.Bundle;
import android.widget.Toast;


public class EnterResultsActivity extends AppCompatActivity {

    private EditText results;

    private Button submit;

    private Button photo;

    private String resultsStr;
    private Uri imageUri;

    //for firebase
    public static final String COMPLETED_FIELD= "completed";
    public static final String RESULT_FIELD = "result";
    public static final String TAG = "creatingACT";



    private int buttonClickCounter = 0;
    private ACTObject actObj;
    private int camPermission = 2;
    private int storagePermission = 2;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.enter_results);

        actObj = new ACTObject();

        //get ACT title from intent
        String actTitle = getIntent().getStringExtra("actTitle");

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        // There are no request codes
//                        Intent data = result.getData();
                        if(data != null) {
                            imageUri = data.getData();
                            if (imageUri != null) postToInstagram();
                            else Log.e("URI Error", "Image Uri was null");
                        }
                    }
                });

        results = (EditText) findViewById(R.id.resultsInputEditTextNumber);

        //Set up submit button
        submit = (Button) findViewById(R.id.submitButton);
        submit.setOnClickListener(v ->
        {
            resultsStr = results.getText().toString();
            if (buttonClickCounter == 0 && !resultsStr.isEmpty()){
                Toast.makeText(EnterResultsActivity.this,
                        "Click 'Take Photo' button if you would like a winning team picture.", Toast.LENGTH_LONG).show();
                buttonClickCounter++;
            }
            else {
                if (!resultsStr.isEmpty()) {
                    //updating results and completed fielf of chosen act
                    actObj.enterResult(actTitle, resultsStr);
                    Toast.makeText(EnterResultsActivity.this,
                            "Result successfully entered.", Toast.LENGTH_SHORT).show();

                    finish();
                } else {
                    Toast.makeText(EnterResultsActivity.this,
                            "Please fill out necessary field.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        photo = (Button) findViewById(R.id.photoButton);
        photo.setOnClickListener(v->
        {
            buttonClickCounter++;
            getCameraPermission();
//            getStoragePermissions();
            if(camPermission == 1){
                Intent camera = new Intent(this, CameraActivity.class);
                activityResultLauncher.launch(camera);
            }
            else if (camPermission == 0){
//                if (ActivityCompat.checkSelfPermission(EnterResultsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//                ActivityCompat.requestPermissions(EnterResultsActivity.this, new String[]{Manifest.permission.CAMERA}, 100);}
            }
        });
    }

    private void getCameraPermission(){
       if (ActivityCompat.checkSelfPermission(EnterResultsActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
           Log.i("Permission", "Not Granted");
           ActivityCompat.requestPermissions(EnterResultsActivity.this, new String[]{Manifest.permission.CAMERA}, 100);
       }
       else camPermission = 1;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                camPermission = 1;
            }
            else {
                camPermission = 0;
                Toast.makeText(getApplicationContext(), "Feature won't work without permissions", Toast.LENGTH_LONG).show();
            }
        }
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
            Log.e("postToInstagram", "ActivityNotFoundException");
        }
    }
}
