package com.example.mariokarttournamentorganizer;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ACTObject {

    public static final String ID_FIELD = "ID";
    public static final String ADMIN_FIELD = "adminID";
    public static final String DATE_FIELD = "date";
    public static final String TIME_FIELD = "time";
    public static final String LOCATION_FIELD = "location";
    public static final String PLAYERS_FIELD = "players";
//    public static final String TAG = "creatingACT";
    public static final String COMPLETED_FIELD = "completed";
    public static final String RESULT_FIELD = "result";
//    public static final String TIMESTAMP_FIELD = "Date/Time";

    private static final String TAG = "ACTObject";
    private static final String COLLECTION = "act_objects";
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private CollectionReference coll = db.collection(COLLECTION);

    public int ID;
    public String adminID;
    public String date;
    public String time;
    public String location;
    public ArrayList<String> players;
    public boolean completed;
    public int result;
    // timestamp not saved


    public ACTObject() {
    }

    public void createACT(String admin, String dateStr, String timeStr, String locationStr){
        Map<String, Object> act1 = new HashMap<>();
        act1.put(ADMIN_FIELD, admin);
//        act1.put(TIMESTAMP_FIELD, Timestamp.now());
        act1.put(COMPLETED_FIELD, false); //default
        act1.put(DATE_FIELD, dateStr);
        act1.put(TIME_FIELD, timeStr);
        act1.put(LOCATION_FIELD, locationStr);
        act1.put(PLAYERS_FIELD, Arrays.asList(admin));
        act1.put(RESULT_FIELD, null);

        Query query = coll;
        AggregateQuery countQuery = query.count();

        //getting count of act_objects to generate new ID
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(new OnCompleteListener<AggregateQuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<AggregateQuerySnapshot> task) {
                if (task.isSuccessful()) {
                    // Count fetched successfully
                    AggregateQuerySnapshot snapshot = task.getResult();
                    long count = snapshot.getCount();
                    String docPath = "act" + count;
                    Log.d(TAG, "Count: " + count);
                    Log.d(TAG, "Document name:" + docPath);

                    act1.put(ID_FIELD, count);
                    coll.document(docPath).set(act1)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "DocumentSnapshot written, act"+count);
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error writing document", e);
                                }
                            });
                } else {
                    Log.d(TAG, "Count failed: ", task.getException());
                }
            }
        });
    }
}
