package com.example.metapigeon;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class MenuController extends FragmentPagerAdapter {

    int numOptions;

    public MenuController(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
        //Variable to get number of menu options
        numOptions = behavior;
    }//MenuController

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 1: return new MonsterbookFragment();
            case 2: return new MySpellsFragment();
            default: return new SpellbookFragment();
        }
    }//getItem

    @Override
    public int getCount() {
        return numOptions;
    }//getCount

}//class
