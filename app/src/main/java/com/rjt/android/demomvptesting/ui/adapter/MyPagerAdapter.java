package com.rjt.android.demomvptesting.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rjt.android.demomvptesting.ui.map.LocationListFragment;
import com.rjt.android.demomvptesting.ui.map.SimpleMapFragment;

public class MyPagerAdapter extends FragmentStatePagerAdapter{
    int tabCount;
    public MyPagerAdapter(FragmentManager fm, int tabCount) {
        super(fm);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                LocationListFragment tab1 = LocationListFragment.newInstance();
                return tab1;

            case 1:
                SimpleMapFragment tab2 = SimpleMapFragment.newInstance();
                return tab2;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
