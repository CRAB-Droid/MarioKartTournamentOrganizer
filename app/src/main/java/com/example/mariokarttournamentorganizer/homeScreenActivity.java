package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class homeScreenActivity extends AppCompatActivity {


    RecyclerView upcomingRecyclerView;
    RecyclerView pastRecyclerView;
    Button createACTButton;
    TextView infoTextView;
    String [] upcomingArray = new String[10];
    public String [] pastArray = new String[10];
//    ArrayList<String> upcomingArray = new ArrayList<>();
//    ArrayList<String> pastArray = new ArrayList<>();


    public static final String ACTNAME_KEY = "name";
    public static final String DATE_KEY = "Date/Time";
    //private CollectionReference colllectionRef = FirebaseFirestore.getInstance().collection("act_objects");
    private CollectionReference collection = FirebaseFirestore.getInstance().collection("act_objects");
    //private CollectionReference collectionRef = dataBase.collection("act_objects");







    @Override
    protected void onStart() {
        super.onStart();

        collection
                //.whereEqualTo("state", "CA")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            Log.w("TAG", "Listen failed.", e);
                            return;
                        }



                        for (QueryDocumentSnapshot doc : value) {
                            //do something
                            Log.i("TAG", "Something updated");
                        }
                    }
                });
    }








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        createACTButton = findViewById(R.id.createACTButton);
        upcomingRecyclerView = (RecyclerView) findViewById(R.id.upcomingRecyclerView);
        pastRecyclerView = (RecyclerView) findViewById(R.id.pastRecyclerView);
        infoTextView = findViewById(R.id.infoTextView);

        Intent currACT = new Intent(this, UserACTActivity.class);
//        Intent pastACT = new Intent(this, adminActViewActivity.class);

        //Hardcoded values
        for(int i = 0; i<10; i++){
            upcomingArray[i] = "Upcoming Event " + i;
        }


        fillPastACTS();

        //Log.i("Tag", pastArray[0]);


        //Set up recycle view for the upcoming ACT events.//
        RecyclerViewAdapter upcomingCustomAdapter = new RecyclerViewAdapter(upcomingArray);
        upcomingRecyclerView.setAdapter(upcomingCustomAdapter);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        //Handle the clicking of certain rows, where position is the item clicked.
        ItemClickSupport.addTo(upcomingRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                infoTextView.setText(upcomingArray[position]);
                startActivity(currACT);
            }
        });

        //Set up recycle view for the past ACT events.//////////////
//        RecyclerViewAdapter pastCustomAdapter = new RecyclerViewAdapter(pastArray);
//        pastRecyclerView.setAdapter(pastCustomAdapter);
//        pastRecyclerView.setLayoutManager(new LinearLayoutManager(this));
//
//        ItemClickSupport.addTo(pastRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
//            @Override
//            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
//                infoTextView.setText(pastArray[position]);
//                startActivity(pastACT);
//            }
//        });

          createACTButton.setOnClickListener(v->
          {
              Intent CreateACT = new Intent(this, CreateACTActivity.class);
              startActivity(CreateACT);
          });
    }


    public void firebaseDemo(View v) { // Delete when the demo is gone
        Intent startDemo = new Intent(this, FirebaseDemoActivity.class);
        startActivity(startDemo);
    }

    private void fillPastACTS(){
//        Log.i("Hereeeeeeee", "checkpoint 1");
//
            collection
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        ///Log.i("Hereeeeeeee", "checkpoint 2");
                        if (task.isSuccessful()) {
//                            int size = task.getResult().size();
//                            pastArray = new String[size];
                            int i = 0;
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                //Log.d(TAG, document.getId() + " => " + document.getData());
                                pastArray[i] = document.getId().toString();// + ", " + document.getDate(DATE_KEY);
                                //Log.i("Tag", pastArray[i]);
                                i++;
                                //Log.i("Tag", document.getId().toString());
                            }
                            setUpPastRecycleView();
                        } else {
                            Log.d("TagError", "Error getting documents: ", task.getException());
                        }
                    }
                });
        //Log.i("Tag1", pastArray[0]);
    }

    private void setUpPastRecycleView(){

        Intent pastACT = new Intent(this, adminActViewActivity.class);

        RecyclerViewAdapter pastCustomAdapter = new RecyclerViewAdapter(pastArray);
        pastRecyclerView.setAdapter(pastCustomAdapter);
        pastRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ItemClickSupport.addTo(pastRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                infoTextView.setText(pastArray[position]);
                startActivity(pastACT);
            }
        });
    }

}
