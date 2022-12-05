package com.example.metapigeon;

import static com.example.metapigeon.SpellActivity.NOTIFICACION_ID;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationManagerCompat;
import androidx.viewpager.widget.ViewPager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MenuActivity extends AppCompatActivity {

    //Instances
    TabLayout menu;
    ViewPager fragment;
    MenuController menuCtrl;
    String registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Associate instances with graphic components
        menu = findViewById(R.id.tabLayout);
        fragment = findViewById(R.id.vwpFragment);

        String menuFragment = getIntent().getStringExtra("option");

        //An instance of the menu controller is created
        menuCtrl = new MenuController(getSupportFragmentManager(), menu.getTabCount());
        //It establishes who controls the change of options
        fragment.setAdapter(menuCtrl);

        if(menuFragment != null){
            //Eliminar la notificación de barra de estado
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
            notificationManagerCompat.cancel(NOTIFICACION_ID);

            if (menuFragment.equals("2")) {
                fragment.setCurrentItem(2);
                menuCtrl.getItem(2);
                TabLayout.Tab tab = menu.getTabAt(2);
                tab.select();
            }
        }

        //A listener is created for the menu, which allows defining action by tab
        menu.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                //The chosen option is identified
                fragment.setCurrentItem(tab.getPosition());
                //For chosen option, notify the change
                switch (tab.getPosition()){
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                        menuCtrl.notifyDataSetChanged(); break;
                }
            }//onTabSelected

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }//onTabUnselected

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }//onTabReselected
        });

        String user;
        SharedPreferences metadata = getSharedPreferences("user.dat",MODE_PRIVATE);
        user = metadata.getString("user",null);
        Toast.makeText(getApplicationContext(), "Welcome " + user, Toast.LENGTH_LONG).show();

        //Associate the menu with the Viewpager
        fragment.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(menu));

    }//onCreate

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        //validar si recibe información
        if(intentResult != null){
            //validar si se cancela la lectura se envíe un mensaje
            if(intentResult.getContents() == null){
                Toast.makeText(this, "Lectura Cancelada", Toast.LENGTH_SHORT).show();
            }else{
                //en caso de no calcelar se envía un mensaje y se envía la información a la caja de texto
                //Toast.makeText(this, "Datos Leídos", Toast.LENGTH_SHORT).show();
                registro = intentResult.getContents();
                Intent intent = new Intent(this, CompActivity.class);
                intent.putExtra("mycode", registro);
                startActivity(intent);
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

}