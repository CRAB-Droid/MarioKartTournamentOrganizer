package com.example.mariokarttournamentorganizer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class homeScreenActivity extends AppCompatActivity {


    RecyclerView upcomingRecyclerView;
    RecyclerView pastRecyclerView;
    Button createACTButton;

    String [] upcomingArray = new String[10];
    String [] pastArray = new String[10];
//    ArrayList<String> upcomingArray = new ArrayList<>();
//    ArrayList<String> pastArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_screen);

        createACTButton = findViewById(R.id.createACTButton);
        upcomingRecyclerView = (RecyclerView) findViewById(R.id.upcomingRecyclerView);
        pastRecyclerView = (RecyclerView) findViewById(R.id.pastRecyclerView);


        ////////////Hard coding array values/////////////////////////
        ///////////////////DELETE LATER//////////////////////////////
        upcomingArray[0] = "Upcoming Event 1";
        upcomingArray[1] = "Upcoming Event 2";
        upcomingArray[2] = "Upcoming Event 3";

        for(int i = 0; i<10; i++){
            pastArray[i] = "Past Event " + i;
        }
        /////////////////////////////////////////////////////////////

        //Set recycle view for the upcoming ACT events.
        RecyclerViewAdapter upcomingCustomAdapter = new RecyclerViewAdapter(upcomingArray);
        upcomingRecyclerView.setAdapter(upcomingCustomAdapter);
        upcomingRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //upcomingCustomAdapter.addData(upcomingArray);

        //Set recycle view for the past ACT events.
        RecyclerViewAdapter pastCustomAdapter = new RecyclerViewAdapter(pastArray);
        pastRecyclerView.setAdapter(pastCustomAdapter);
        pastRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //pastCustomAdapter.addData(pastArray);

        createACTButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //switchToStateXXX();
                upcomingArray[0] = "weird";
            }
        });
    }

    ///////////////////Handling Arrays////////////////
//    /**
//     * Add to either past or upcoming array.
//     * @param array The array you want to modify.
//     */
//    private void addToArray(ArrayList<String> array, String info){
//        array.add(info);
//    }
    //////////////////////////////////////////////////


    /////////////////Switching Screens////////////////
    private void switchToStateXXX(){
        //Intent toNextState = new Intent(this, XXX.class);
        //startActivity(XXX);
    }
    //////////////////////////////////////////////////

}
