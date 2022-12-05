package com.example.metapigeon;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.metapigeon.ui.main.DBController;

public class CompActivity extends AppCompatActivity {
    ImageView images;
    TextView title, descrip;
    GalleryAdapter adapt;
    DBController adm;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comp);

        images = findViewById(R.id.imvPicBook);
        title = findViewById(R.id.txtNamBook);
        descrip = findViewById(R.id.txtDesBook);

        adm = new DBController(this, "metapigeonDB", null, 1);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("BOOK");
        Intent intent = getIntent();

        if(intent.getExtras().containsKey("mybooks")){
            int position = intent.getExtras().getInt("mybooks");
            adapt = new GalleryAdapter(this);
            images.setImageResource(adapt.imageArray[position]);
            searchBookID(position);
        }else {
            String registro = intent.getExtras().getString("mycode");
            int re = searchBook(registro);
            adapt = new GalleryAdapter(this);
            images.setImageResource(adapt.imageArray[re]);
        }

    }//onCreate

    private int searchBook(String reg) {
        int r=0;
        SQLiteDatabase bd = adm.getReadableDatabase();
        Cursor fila = bd.rawQuery("select * from book where reg='" + reg +"'", null);

        if (fila.moveToFirst()) {
            r=fila.getInt(0);
            title.setText(fila.getString(2));
            descrip.setText(fila.getString(3));

        } else {
            Toast.makeText(this, "Código no existe", Toast.LENGTH_SHORT).show();
            Intent ex = new Intent(this, MenuActivity.class);
            startActivity(ex);
        }
        bd.close();
        return r;
    }

    private void searchBookID(int id) {
        SQLiteDatabase bd = adm.getReadableDatabase();

        Cursor fila = bd.rawQuery("select * from book where id=" + id, null);

        if (fila.moveToFirst()) {

            title.setText(fila.getString(2));
            descrip.setText(fila.getString(3));

        } else {
            Toast.makeText(this, "Código no existe", Toast.LENGTH_SHORT).show();
        }
        bd.close();
    }

}//CompActivity