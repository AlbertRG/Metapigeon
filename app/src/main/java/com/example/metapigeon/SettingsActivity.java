package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SettingsActivity extends AppCompatActivity {

    private String url;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        ImageView back = findViewById(R.id.imgBack);
        ImageView kofi = findViewById(R.id.imgKofi);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        url="https://ko-fi.com/";

        kofi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse(url);
                intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });

    }//onCreate

    public void logout(View view){
        //clear SharePreferences
        SharedPreferences metadata = getSharedPreferences("user.dat", MODE_PRIVATE);
        //SharedPreferences.Editor edit = metadata.edit();
        metadata.edit().clear().apply();

        //Back to Login
        Intent intent = new Intent(this, LoginActivity.class);
        //Flags to start the application like first time
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }//logout
}//class