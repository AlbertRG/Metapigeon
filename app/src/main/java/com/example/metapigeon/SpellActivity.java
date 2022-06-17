package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SpellActivity extends AppCompatActivity {

    ImageView back, add;
    TextView name, school, source, time, range, component, duration, classes, description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spell);

        back = findViewById(R.id.imgBackSpell);
        add = findViewById(R.id.imgAddSpell);
        name = findViewById(R.id.txtNameSpell);
        school = findViewById(R.id.txtSchool);
        source = findViewById(R.id.txtSourceInfo);
        time = findViewById(R.id.txtTimeInfo);
        range = findViewById(R.id.txtRangeInfo);
        component = findViewById(R.id.txtCompoenentsInfo);
        duration = findViewById(R.id.txtDurationInfo);
        classes = findViewById(R.id.txtClassesInfo);
        description = findViewById(R.id.txtDescriptionSpell);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String account;
                SharedPreferences metadata = getSharedPreferences("user.dat",MODE_PRIVATE);
                account = metadata.getString("email",null);
                addMyList(name.getText().toString(),account + ".spells");
            }
        });

        String spell;
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if(extras == null) {
                spell= null;
            } else {
                spell= extras.getString("SpellSelected");
            }
        } else {
            spell= (String) savedInstanceState.getSerializable("SpellSelected");
        }

        openSpell(spell);

    }//onCreate

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

    private void openSpell(String spell) {
        //Get list of internal files
        String[] fileList = fileList();
        //Validate file
        if( checkFiles(fileList, spell)){
            try{
                //Associate file to instance
                InputStreamReader internalFile = new InputStreamReader(openFileInput(spell));
                //Instance to read file
                BufferedReader spellFile = new BufferedReader(internalFile);
                //Read the content of the file and put it in a variable
                String line = spellFile.readLine();
                name.setText(line);
                line = spellFile.readLine();
                school.setText(line);
                line = spellFile.readLine();
                source.setText(line);
                line = spellFile.readLine();
                time.setText(line);
                line = spellFile.readLine();
                range.setText(line);
                line = spellFile.readLine();
                component.setText(line);
                line = spellFile.readLine();
                duration.setText(line);
                line = spellFile.readLine();
                classes.setText(line);
                line = spellFile.readLine();
                description.setText(line);

                spellFile.close();
                internalFile.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR loading spell",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "ERROR the spell does not exist",Toast.LENGTH_LONG).show();
        }
    }//openSpell

    //Open accountFile inside the device
    private void addMyList(String spell, String file) {
        //Get list of internal files
        String[] fileList = fileList();
        String[] mySpells = new String[20];
        int i = 0;
        //Validate file
        if(!checkFiles(fileList,file)){
            try{
                OutputStreamWriter internalFile = new OutputStreamWriter(openFileOutput(file, Activity.MODE_PRIVATE));
                internalFile.write(spell);
                internalFile.flush();
                internalFile.close();
                Toast.makeText(getApplicationContext(), spell + " added", Toast.LENGTH_SHORT).show();
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR adding spell", Toast.LENGTH_LONG).show();
            }
        }else{
            try{
                InputStreamReader internalFileReader = new InputStreamReader(openFileInput(file));
                BufferedReader spellFile = new BufferedReader(internalFileReader);

                String line = spellFile.readLine();
                while (line != null){
                    mySpells[i] = line;
                    line = spellFile.readLine();
                    i++;
                }
                spellFile.close();
                internalFileReader.close();

                OutputStreamWriter internalFileWriter = new OutputStreamWriter(openFileOutput(file, Activity.MODE_PRIVATE));
                i = 0;

                while (mySpells[i] != null){
                    internalFileWriter.write(mySpells[i] + "\n");
                    internalFileWriter.flush();
                    i++;
                }
                internalFileWriter.write(spell);
                internalFileWriter.flush();
                internalFileWriter.close();
                Toast.makeText(getApplicationContext(), spell + " added", Toast.LENGTH_SHORT).show();
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR adding spell", Toast.LENGTH_LONG).show();
            }
        }
    }//openAccount

}