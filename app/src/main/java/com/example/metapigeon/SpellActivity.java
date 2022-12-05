package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.app.Activity;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;
import android.os.Build;
import com.example.metapigeon.ui.main.DBController;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class SpellActivity extends AppCompatActivity {

    ImageView back, add;
    TextView name, school, source, time, range, component, duration, classes, description;
    private DBController admin;
    private PendingIntent pendingIntentSi;
    private PendingIntent pendingIntentNo;
    private final static String CHANNEL_ID = "NOTIFICACION";
    public final static int NOTIFICACION_ID = 0;

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

        admin = new DBController(this,"metapigeonDB",null,1);

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

    private void openSpell(String spell) {

        SQLiteDatabase bd = admin.getReadableDatabase();
        String[] nameSpell = {spell};

        //name text primary key, school text, source text, time text, range text, component text, duration text, classes text, description text
        Cursor fila = bd.rawQuery("select * from spells where name = ?", nameSpell);

        fila.moveToFirst();
        String dbname = fila.getString(0);
        String dbschool = fila.getString(1);
        String dbsource = fila.getString(2);
        String dbtime = fila.getString(3);
        String dbrange = fila.getString(4);
        String dbcomponent = fila.getString(5);
        String dbduration = fila.getString(6);
        String dbclasses = fila.getString(7);
        String dbdescription = fila.getString(8);
        bd.close();

        name.setText(dbname);
        school.setText(dbschool);
        source.setText(dbsource);
        time.setText(dbtime);
        range.setText(dbrange);
        component.setText(dbcomponent);
        duration.setText(dbduration);
        classes.setText(dbclasses);
        description.setText(dbdescription);

        Log.i("DB_Select", dbname);

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
                setPendingIntentNo();
                setPendingIntentSi();
                crearCanalNotificacion();
                crearNotificacion(spell + " added");
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
                setPendingIntentNo();
                setPendingIntentSi();
                crearCanalNotificacion();
                crearNotificacion(spell + " added");
            } catch(IOException e) {
                Toast.makeText(getApplicationContext(), "ERROR adding spell", Toast.LENGTH_LONG).show();
            }
        }
    }//openAccount

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

    private void setPendingIntentSi() {
        Intent intent1 = new Intent(SpellActivity.this, MenuActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent1.putExtra("option", "2");
        TaskStackBuilder stackBuilder1 = TaskStackBuilder.create(this);
        stackBuilder1.addParentStack(MenuActivity.class);
        stackBuilder1.addNextIntent(intent1);
        pendingIntentSi = stackBuilder1.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }//setPendingIntentSi

    private void setPendingIntentNo() {
        Intent intent = new Intent(SpellActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("option", "0");
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(intent);
        pendingIntentNo = stackBuilder.getPendingIntent(1, PendingIntent.FLAG_UPDATE_CURRENT);
    }//setPendingIntentNo

    //Mètodo parar definir el canal de comunicación para enviar la notificación
    private void crearCanalNotificacion() {
        //Validar si es versiòn Android superior a O (API >=26 )
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //Nombre del canal
            CharSequence name = "Notificación";
            //Instancia para gestionar el canal y el servicio de la notificación
            NotificationChannel notificationChannel = new
                    NotificationChannel(CHANNEL_ID, name,
                    NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(notificationChannel);
        }//if
    }//crearCanalNotificacion

    private void crearNotificacion(String message) {
        //Instancia para generar la notificaciòn, especificando el contexto de la aplicación y el canal de comunicación
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),CHANNEL_ID);
        //Características a incluir en la notificación
        builder.setSmallIcon(R.drawable.spell);
        builder.setContentTitle(message);
        builder.setContentText("Do you want to see your current spells?");
        builder.setColor(Color.BLUE);
        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);
        builder.setLights(Color.RED, 1000,1000);
        builder.setVibrate(new long[]{1000,1000,1000,1000,1000});
        builder.setDefaults(Notification.DEFAULT_SOUND);
        //Especifica la Activity que aparece al momento de elegir la notificación
        builder.setContentIntent(pendingIntentSi);
        //Se agregan las opciones que aparecen en la notificación
        builder.addAction(R.drawable.ic_thumb_up_black_24dp,"Yes",pendingIntentSi);
        builder.addAction(R.drawable.ic_thumb_down_black_24dp,"No",pendingIntentNo);
        //Instancia que gestiona la notificación con el dispositivo
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        notificationManagerCompat.notify(NOTIFICACION_ID, builder.build());
    }//crearNotificacion

}