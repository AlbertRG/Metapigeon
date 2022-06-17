package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity {

    //Instances
    private EditText email, password;
    private CheckBox remember;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Associate instances with graphic components
        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPass);
        remember = findViewById(R.id.cbxRemember);
        TextView forgot = findViewById(R.id.txtForgot);
        TextView signup = findViewById(R.id.txtAccount);

        forgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }//onCreate

    public void Login(View view){
        openAccount();
    }//Login

    public void Exit(View view){
        finishAffinity();
    }

    //Validate files within the device
    private boolean checkFiles (String[] filesList, String fileSearch){
        //Loop through the list of files to validate that they exist
        for (String file : filesList) {
            if (fileSearch.equals(file)) {
                return true;
            }
        }
        return false;
    }//checkFiles

    //Open accountFile inside the device
    private void openAccount() {
        //Get list of internal files
        String[] fileList = fileList();
        String accountFile = email.getText().toString().trim() + ".txt";
        //Validate file
        if(checkFiles(fileList, accountFile)){
            try{
                //Associate file to instance
                InputStreamReader internalFile = new InputStreamReader(openFileInput(accountFile));
                //Instance to read file
                BufferedReader FileReader = new BufferedReader(internalFile);
                //Read the content of the file and put it in a variable
                String line = FileReader.readLine();
                String email = line;
                line = FileReader.readLine();
                String password = line;
                line = FileReader.readLine();
                String username = line;
                //Compare file info vs components info
                if(email.equals(this.email.getText().toString().trim()) && password.equals(this.password.getText().toString().trim())){
                    //Toast.makeText(getApplicationContext(), "Login",Toast.LENGTH_SHORT).show();
                    savePreferencesNeeded(email, username);
                    //Save preferences if user selects it
                    if(remember.isChecked()){
                        savePreferences(password);
                    }
                    intent = new Intent(getApplicationContext(), MenuActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Email or Password incorrect",Toast.LENGTH_LONG).show();
                }
                FileReader.close();
                internalFile.close();
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR login",Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "ERROR the account does not exist",Toast.LENGTH_LONG).show();
        }
    }//openAccount

    private void savePreferences (String password){
        //Create object to store username and password information
        SharedPreferences metadata = getSharedPreferences("user.dat",MODE_PRIVATE);
        SharedPreferences.Editor edit = metadata.edit();
        edit.putString("password", password);
        edit.putBoolean("register", true);
        edit.apply();
    }//savePreferences

    private void savePreferencesNeeded (String email, String user){
        //Create object to store username and password information
        SharedPreferences metadata = getSharedPreferences("user.dat",MODE_PRIVATE);
        SharedPreferences.Editor edit = metadata.edit();
        edit.putString("email", email);
        edit.putString("user", user);
        edit.apply();
    }//savePreferencesNeeded

}//class