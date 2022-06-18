package com.example.metapigeon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    //Instances
    private EditText email, password;
    private CheckBox remember;
    private Intent intent;
    SignInButton signInButton;
    private GoogleApiClient googleApiClient;
    TextView textView;
    private static final int RC_SIGN_IN = 1;

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

        GoogleSignInOptions gso =  new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        googleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        signInButton=(SignInButton)findViewById(R.id.sign_in_button);
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
                startActivityForResult(intent,RC_SIGN_IN);
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }

    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
            gotoProfile();
        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }

    private void gotoProfile(){
        Intent intent=new Intent(LoginActivity.this,MenuActivity.class);
        startActivity(intent);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}//class