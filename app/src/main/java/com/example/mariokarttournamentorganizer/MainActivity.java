package com.example.mariokarttournamentorganizer;

import android.app.Activity;
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
                activityResultLauncher.launch(open_camera);
            }
        });
    }

    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    Bundle bundle = result.getData().getExtras();
                    Bitmap photo = (Bitmap) bundle.get("data");
                    imageView.setImageBitmap(photo);

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
            });

    void postToInstagram(Uri backgroundAssetUri) {
        // background asset means the main photo on the story,
            // sticker asset is the alternative

        // Instantiate an intent
        Intent intent = new Intent("com.instagram.share.ADD_TO_STORY");

        // Attach your App ID to the intent
        String sourceApplication = "@string/facebook_app_id"; // This is your application's FB ID
        intent.putExtra("source_application", sourceApplication);

        // Attach your image to the intent from a URI
//        Uri backgroundAssetUri = Uri.parse("your-image-asset-uri-goes-here");
        intent.setDataAndType(backgroundAssetUri, "image/jpeg");

        // Grant URI permissions for the image
        intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        // Instantiate an activity
        Activity activity = this; // would have to use getActivity() if we're in a fragment

        // Verify that the activity resolves the intent and start it
        if (activity.getPackageManager().resolveActivity(intent, 0) != null) {
            activity.startActivityForResult(intent, 0);
        } else {
            System.out.println("not allowed");
        }
    }
}