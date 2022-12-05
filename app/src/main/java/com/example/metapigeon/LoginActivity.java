package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.metapigeon.ui.main.DBController;

public class LoginActivity extends AppCompatActivity {

    //Instances
    private EditText email, password;
    private CheckBox remember;
    private Intent intent;
    private DBController admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        admin = new DBController(this,"metapigeonDB",null,1);

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

    private void openAccount() {

        String accountEmail = email.getText().toString().trim();

        if(searchAccount(accountEmail)){

            SQLiteDatabase bd = admin.getReadableDatabase();
            String[] userEmail = {accountEmail};

            Cursor fila = bd.rawQuery("select username, password from users where email = ?", userEmail);

            fila.moveToFirst();
            String username = fila.getString(0);
            String password = fila.getString(1);
            bd.close();

            //Compare database info vs components info
            if(password.equals(this.password.getText().toString().trim())){

                Log.i("Login Correct",  accountEmail);

                savePreferencesNeeded(accountEmail, username);

                //Save preferences if user selects it
                if(remember.isChecked()){
                    savePreferences(password);
                }

                intent = new Intent(getApplicationContext(), IntroActivity.class);
                startActivity(intent);
                finish();

            } else {
                Toast.makeText(getApplicationContext(), "Email or Password incorrect",Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(getApplicationContext(), "ERROR the account does not exist",Toast.LENGTH_SHORT).show();
        }

    }//openAccount

    private boolean searchAccount(String email){

        SQLiteDatabase bd = admin.getReadableDatabase();
        String[] userEmail = {email};

        Cursor fila = bd.rawQuery("select * from users where email = ?", userEmail);

        if (fila.moveToFirst()){

            bd.close();
            return(true);

        } else {

            bd.close();
            return(false);

        }

    }//searchAccount

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

}//LoginActivity