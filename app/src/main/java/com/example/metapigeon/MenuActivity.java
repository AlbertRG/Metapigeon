package com.example.metapigeon;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import com.google.android.material.tabs.TabLayout;

public class MenuActivity extends AppCompatActivity {

    //Instances
    TabLayout menu;
    ViewPager fragment;
    MenuController menuCtrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        //Associate instances with graphic components
        menu = findViewById(R.id.tabLayout);
        fragment = findViewById(R.id.vwpFragment);

        //An instance of the menu controller is created
        menuCtrl = new MenuController(getSupportFragmentManager(), menu.getTabCount());
        //It establishes who controls the change of options
        fragment.setAdapter(menuCtrl);
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

}