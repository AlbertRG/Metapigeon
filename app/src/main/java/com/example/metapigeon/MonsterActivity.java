package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.metapigeon.ui.main.DBController;

public class MonsterActivity extends AppCompatActivity {

    ImageView back;
    TextView name, type, ac, hp, speed, str, dex, con, intel, wis, cha;
    private DBController admin;

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

        admin = new DBController(this,"metapigeonDB",null,1);

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

    private void openMonster(String monster) {

        SQLiteDatabase bd = admin.getReadableDatabase();
        String[] nameMonster = {monster};

        //name text primary key, type text, ac int, hp int, speed text, str int, dex int, con int, intel int, wis int, cha int
        Cursor fila = bd.rawQuery("select * from monsters where name = ?", nameMonster);

        fila.moveToFirst();
        String dbname = fila.getString(0);
        String dbtype = fila.getString(1);
        String dbac = fila.getString(2);
        String dbhp = fila.getString(3);
        String dbspeed = fila.getString(4);
        String dbstr = fila.getString(5);
        String dbdex = fila.getString(6);
        String dbcon = fila.getString(7);
        String dbintel = fila.getString(8);
        String dbwis = fila.getString(9);
        String dbcha = fila.getString(10);
        bd.close();

        name.setText(dbname);
        type.setText(dbtype);
        ac.setText(dbac);
        hp.setText(dbhp);
        speed.setText(dbspeed);
        str.setText(dbstr);
        dex.setText(dbdex);
        con.setText(dbcon);
        intel.setText(dbintel);
        wis.setText(dbwis);
        cha.setText(dbcha);

        Log.i("DB_Select", dbname);

    }//openMonster

}