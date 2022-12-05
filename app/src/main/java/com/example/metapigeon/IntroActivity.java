package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.MediaController;
import android.widget.VideoView;

public class IntroActivity extends AppCompatActivity {

    protected VideoView videoView;
    protected MediaController mediaController;
    private CheckBox dontShow;
    private Button continueButton;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        dontShow = findViewById(R.id.cbxDontShow);
        continueButton = findViewById(R.id.btnContinue);

        if(skipIntro()){
            //If you decided to save your login information
            intent = new Intent(getApplicationContext(), MenuActivity.class);
            startActivity(intent);
            finish();
        }

        videoView = findViewById(R.id.introVideo);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.introrp);
        videoView.setVideoURI(uri);
        videoView.requestFocus();
        videoView.start();
        videoView.setMediaController(mediaController);

        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(dontShow.isChecked()){
                    savePreferencesIntro();
                }

                intent = new Intent(getApplicationContext(), MenuActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }//onCreate

    private boolean skipIntro(){
        SharedPreferences metadata = getSharedPreferences( "user.dat",MODE_PRIVATE);
        //Returns false if there is no previously stored information
        return metadata.getBoolean("skipintro", false);
    }//skipIntro

    private void savePreferencesIntro (){
        //Create object to store username and password information
        SharedPreferences metadata = getSharedPreferences("user.dat",MODE_PRIVATE);
        SharedPreferences.Editor edit = metadata.edit();
        edit.putBoolean("skipintro", true);
        edit.apply();
    }//savePreferencesNeeded

}//IntroActivity