package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.metapigeon.ui.main.DBController;

public class RegisterActivity extends AppCompatActivity {

    //Instances
    private EditText email, password, username;
    private CheckBox termsOfService;
    private Intent intent;
    private DBController admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        admin = new DBController(this,"metapigeonDB",null,1);

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
                if(createAccount()){
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

    private boolean createAccount() {

        String accountEmail = email.getText().toString().trim();

        if(searchAccount(accountEmail)){

            Toast.makeText(getApplicationContext(), "ERROR account already exist", Toast.LENGTH_LONG).show();
            return false;

        }else{

            SQLiteDatabase bd = admin.getWritableDatabase();

            //Objeto que almacena los valores para enviar a la tabla
            ContentValues registro = new ContentValues();

            //Referencias a los datos que pasar a la BD indicando como parámetos de put el nombre del campo y el valor a insertar
            registro.put("username", username.getText().toString().trim());
            registro.put("email", email.getText().toString().trim());
            registro.put("password", password.getText().toString().trim());

            if (bd != null) {

                //Almacenar los valores en la tabla
                long x = 0;
                try {
                    x = bd.insert("users", null, registro);
                } catch (SQLException e) {
                    Log.e("Exception", "Error: " + String.valueOf(e.getMessage()));
                }

                //Cerrar la base de datos
                bd.close();

                //Confirmar la operación realizada
                Log.i("DB_Insert",  accountEmail);

            }

            return true;

        }

    }//createAccount

    private boolean searchAccount(String email){

        SQLiteDatabase bd = admin.getReadableDatabase();
        String[] userEmail = {email};

        //Objeto apunta al registro donde localice el dato, se le envia la instrucción sql de busqueda
        Cursor fila = bd.rawQuery("select * from users where email = ?", userEmail);

        //Valida
        if (fila.moveToFirst()){

            bd.close();
            return(true);

        } else {

            bd.close();
            return(false);

        }

    }//searchAccount

}//RegisterActivity