package com.rjt.android.demomvptesting.ui.adapter;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.rjt.android.demomvptesting.ui.map.LocationListFragment;
import com.rjt.android.demomvptesting.ui.map.SimpleMapFragment;
import com.rjt.android.demomvptesting.ui.map.SimpleMapPresenter;

public class MyPagerAdapter extends FragmentStatePagerAdapter{
    int tabCount;
    Context context;
    public MyPagerAdapter(FragmentManager fm, int tabCount, Context context) {
        super(fm);
        this.tabCount = tabCount;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                SimpleMapFragment tab2 = SimpleMapFragment.newInstance(new SimpleMapPresenter(this.context));
                return tab2;
            case 1:
                LocationListFragment tab1 = LocationListFragment.newInstance();
                return tab1;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
