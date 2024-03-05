package com.example.mariokarttournamentorganizer;

import java.util.Arrays;

public class ACTObject {
    private int ACTID;
    private int ADMINID;
    private boolean COMPLETED = false; //Set to true after act is completed
    //private DateTime; //
    private String LOCATION;
    private int[] PLAYERS; //MAX 8
    private int RESULT;

    //Constructor
    public ACTObject(int actid, int adminid, String location){
        ACTID = actid; //Will be set by firebase most likely
        ADMINID = adminid; //Fetched in firebase from user creating act
        LOCATION = location; //Input by user
        //Date Time will be set in firebase (Input by user though)
        PLAYERS = new int[8];
        Arrays.fill(PLAYERS, 0);
        setPlayer(adminid);
    }

    //Place each player into PLAYERS array
    public boolean setPlayer(int playerid){
        for(int i=0;i<8;i++){
            if(PLAYERS[i]==0) {
                PLAYERS[i] = playerid;
                return true;
            }
        }
        return false;
    }
    //Mark ACT as completed and put in results
    public boolean setCompletedACT(int result){
        //If ACT has already had results input, cannot input new results
        if(COMPLETED) return false;
        //Else input results and mark as completed
        RESULT = result;
        COMPLETED = true;
        return true;
    }

}
