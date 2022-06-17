package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStreamWriter;

public class RegisterActivity extends AppCompatActivity {

    //Instances
    private EditText email, password, username;
    private CheckBox termsOfService;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Associate instances with graphic components
        email = findViewById(R.id.txtSignEmail);
        password = findViewById(R.id.txtSignPassword);
        username = findViewById(R.id.txtSignUser);
        termsOfService = findViewById(R.id.cbxTermOfServices);
        TextView loginHere = findViewById(R.id.txtLoginhere);

        loginHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }//onCreate

    public void signUp(View view){
        if(!email.getText().toString().trim().isEmpty() && !password.getText().toString().trim().isEmpty() && !username.getText().toString().trim().isEmpty()){
            if(termsOfService.isChecked()){
                int status = createAccount();
                if(status == 1){
                    intent = new Intent(getApplicationContext(), LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            } else {
                Toast.makeText(getApplicationContext(), "It is necessary to ACCEPT the terms of service", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "It is necessary to fill ALL the fields", Toast.LENGTH_LONG).show();
        }
    }//signUp

    private int createAccount() {
        String accountFile = email.getText().toString().trim() + ".txt";
        String[] fileList = fileList();
        if(!checkFiles(fileList,accountFile)){
            try{
                OutputStreamWriter internalFile = new OutputStreamWriter(openFileOutput(accountFile, Activity.MODE_PRIVATE));
                internalFile.write(email.getText().toString().trim() + "\n" + password.getText().toString().trim() + "\n" + username.getText().toString().trim());
                internalFile.flush();
                internalFile.close();
                Toast.makeText(getApplicationContext(), "Account created", Toast.LENGTH_LONG).show();
                email.setText("");
                password.setText("");
                username.setText("");
                return 1;
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR creating account", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(getApplicationContext(), "ERROR account already exist", Toast.LENGTH_LONG).show();
        }
        return 0;
    }//createAccount

    private boolean checkFiles (String[] files, String fileSearch){
        //Loop through the list of files to validate that they exist
        for (String file : files) {
            if (fileSearch.equals(file)) {
                return true;
            }
        }
        return false;
    }//checkFiles

}//class