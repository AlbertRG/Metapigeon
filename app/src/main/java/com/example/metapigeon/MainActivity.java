package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TimerTask task = new TimerTask() {

            @Override
            public void run() {

                //Create intent object to assign the corresponding Activity
                Intent intent;

                //Evaluate if there are SharePreferences stored in the mobile
                if(newUser()){
                    //MENU
                    intent = new Intent(MainActivity.this, SpellbookActivity.class);
                } else {
                    //LOGIN
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                }

                //Run the corresponding Activity and delete the current Activity
                startActivity(intent);
                finish();

            }//run
        };//task

        //Object to set the timeout to 1000 milliseconds
        Timer time = new Timer();
        time.schedule(task, 1000);

    }//onCreate

    //Check if there is userdata and password stored in the mobile
    //SharePreferences allows you to store configuration values
    private boolean newUser(){

        //Define the object where the information will be stored
        SharedPreferences metadata = getSharedPreferences( "user.dat",MODE_PRIVATE);
        //Returns false if there is no previously stored information
        return metadata.getBoolean("register", false);

    }//newUser

}//class
