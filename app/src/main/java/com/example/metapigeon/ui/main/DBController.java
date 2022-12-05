package com.example.metapigeon.ui.main;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBController extends SQLiteOpenHelper {

    public DBController(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase metapigeonDB) {

        //Query sql
        String userQuery = "create table if not exists users (id integer primary key autoincrement, username text, email text unique, password text)";
        String spellQuery = "create table if not exists spells (name text primary key, school text, source text, time text, range text, component text, duration text, classes text, description text)";
        String monsterQuery = "create table if not exists monsters (name text primary key, type text, ac int, hp int, speed text, str int, dex int, con int, intel int, wis int, cha int)";
        String sql = "create table book (id int primary key, reg text, title text, descrip text)";

        try{
            //Creación de tabla, con campos y clave primaria
            metapigeonDB.execSQL(userQuery);
            metapigeonDB.execSQL(spellQuery);
            metapigeonDB.execSQL(monsterQuery);
            metapigeonDB.execSQL(sql);
        } catch (SQLException e){
            //Log.e("DB ERROR","Error table creation");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}//class
