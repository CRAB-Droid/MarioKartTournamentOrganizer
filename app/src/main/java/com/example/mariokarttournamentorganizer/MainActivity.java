package com.example.mariokarttournamentorganizer;

import static android.content.Intent.ACTION_SEND;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = findViewById(R.id.myImage);
        button = findViewById(R.id.myButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraLauncher.launch(open_camera);
            }
        });

    }
    ActivityResultLauncher<Intent> cameraLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    processImage(bundle);
                }
            }
    );

    void processImage(Bundle bundle) {
        Bitmap photo = (Bitmap) bundle.get("data");
//        imageView.setImageBitmap(photo);

        // Get photo into Uri form
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, "MyPhoto");
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        // Log to double check something is in the Uri
        if (uri != null) {
            String uriString = uri.toString();
            Log.d("MainActivity", "Uri: " + uriString);
        } else {
            Log.e("MainActivity", "Uri is null");
        }

        // Post to Insta stories
        postToInstagram(uri);
    }



    void postToInstagram(Uri uri) {
        // background asset means the main photo on the story,
            // sticker asset is the alternative

        // Instantiate an intent
        Intent share = new Intent(ACTION_SEND);
        share.setAction(ACTION_SEND);
        share.setType("image/*");

        // Attach your App ID to the intent
        String sourceApplication = "@string/facebook_app_id"; // This is your application's FB ID
        share.putExtra("source_application", sourceApplication);
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Try to invoke the intent.
        try {
            startActivity(Intent.createChooser(share, "Share to"));
        } catch (ActivityNotFoundException e) {
            Log.e("MainActivity", "ActivityNotFoundException");
            // Define what your app should do if no activity can handle the intent.
        }
    }
}