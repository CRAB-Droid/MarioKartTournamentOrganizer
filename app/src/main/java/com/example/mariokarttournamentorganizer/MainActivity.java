package com.example.mariokarttournamentorganizer;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    public static final String QUOTE_KEY = "quote";
    public static final String AUTHOR_KEY = "author";
    public static final String TAG = "InspiringQuote";

    TextView mQuoteTextView;

    private DocumentReference mDocRef = FirebaseFirestore.getInstance().document("sampleData/inspiration");

    @Override
    protected void onStart() {
        super.onStart();
        // dynamically fetch data, without having to click fetch
        // It'll happen really fast, cause Android technically updates from the local cache
        // even before it goes to server. That is just an optimization android does since
        // we're saving and fetching to/from database from the same client.
        mDocRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            // by specifying this, this listener will stop when main activity stops
            // this helps user battery life and our database server costs
            @Override
            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                // if statement and code under it same as when fetching with button
                if (documentSnapshot.exists()) {
                    String quoteText = documentSnapshot.getString(QUOTE_KEY);
                    String authorText = documentSnapshot.getString(AUTHOR_KEY);
                    mQuoteTextView.setText("\"" + quoteText + "\" -- " + authorText);
                } else if (e != null) {
                    Log.w(TAG, "Got an exception!", e);
                }
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQuoteTextView = (TextView) findViewById(R.id.textViewInspiring);
    }

    public void fetchQuote(View view) {
        // use the mDocRef variable for saving and fetching to/from db, since we want to edit the...
        // ... same FireStore "document"
        mDocRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                // DocumentSnapshot has info related to document that we're fetching from db
                if (documentSnapshot.exists()) {
                    String quoteText = documentSnapshot.getString(QUOTE_KEY);
                    String authorText = documentSnapshot.getString(AUTHOR_KEY);
                    // Alternatively could to this to get entire document in String, Object hashmap
                    // Map<String, Object> myData = documentSnapshot.getData();
                    mQuoteTextView.setText("\"" + quoteText + "\" -- " + authorText);
                }
            }
        });
    }

    public void saveQuote(View view) {
        EditText quoteView = (EditText) findViewById(R.id.editTextQuote);
        EditText authorView = (EditText) findViewById(R.id.editTextAuthor);
        String quoteText = quoteView.getText().toString();
        String authorText = authorView.getText().toString();

        if (quoteText.isEmpty() || authorText.isEmpty()) {
            return;
        }
        Map<String, Object> dataToSave = new HashMap<String, Object>();
        dataToSave.put(QUOTE_KEY, quoteText);
        dataToSave.put(AUTHOR_KEY, authorText);
        mDocRef.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG, "Document has been saved!");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.w(TAG, "Document was not saved!");
            }
        });

        Log.d("MainActivity", "Quote saved");
    }
}