package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MonsterActivity extends AppCompatActivity {

    ImageView back;
    TextView name, type, ac, hp, speed, str, dex, con, intel, wis, cha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monster);

        back = findViewById(R.id.imgBackMonster);

        name = findViewById(R.id.txtNameMonster);
        type = findViewById(R.id.txtType);
        ac = findViewById(R.id.txtACInfo);
        hp = findViewById(R.id.txtHPInfo);
        speed = findViewById(R.id.txtSpeedInfo);
        str = findViewById(R.id.txtStrInfo);
        dex = findViewById(R.id.txtDexInfo);
        con = findViewById(R.id.txtConInfo);
        intel = findViewById(R.id.txtIntInfo);
        wis = findViewById(R.id.txtWisInfo);
        cha= findViewById(R.id.txtChaInfo);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        String monster;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                monster = null;
            } else {
                monster = extras.getString("MonsterSelected");
            }
        } else {
            monster = (String) savedInstanceState.getSerializable("MonsterSelected");
        }

        openMonster(monster);

    }

    //Validate files within the device
    private boolean checkFiles (String[] files, String fileSearch){
        //Loop through the list of files to validate that they exist
        for (String file : files) {
            if (fileSearch.equals(file)) {
                return true;
            }
        }
        return false;
    }//checkFiles

    private void openMonster(String monster) {
        //Get list of internal files
        String[] fileList = fileList();
        //Validate file
        if( checkFiles(fileList, monster)){
            try{
                //Associate file to instance
                InputStreamReader internalFile = new InputStreamReader(openFileInput(monster));
                //Instance to read file
                BufferedReader monsterFile = new BufferedReader(internalFile);
                //Read the content of the file and put it in a variable
                String line = monsterFile.readLine();
                name.setText(line);
                line = monsterFile.readLine();
                type.setText(line);
                line = monsterFile.readLine();
                ac.setText(line);
                line = monsterFile.readLine();
                hp.setText(line);
                line = monsterFile.readLine();
                speed.setText(line);
                line = monsterFile.readLine();
                str.setText(line);
                line = monsterFile.readLine();
                dex.setText(line);
                line = monsterFile.readLine();
                con.setText(line);
                line = monsterFile.readLine();
                intel.setText(line);
                line = monsterFile.readLine();
                wis.setText(line);
                line = monsterFile.readLine();
                cha.setText(line);

                monsterFile.close();
                internalFile.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR loading monster",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "ERROR the monster does not exist",Toast.LENGTH_LONG).show();
        }
    }//openMonster
}