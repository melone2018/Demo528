package com.rjt.android.demomvptesting.ui.map;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TableLayout;

import com.rjt.android.demomvptesting.ui.adapter.MyPagerAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.rjt.android.demomvptesting.R;
import com.rjt.android.demomvptesting.data.repository.DataManager;
import com.rjt.android.demomvptesting.data.repository.local.LocalDataSource;
import com.rjt.android.demomvptesting.data.repository.remote.RemoteDataSource;

public class MapActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DataManager dataManager;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mTabLayout = findViewById(R.id.tablayout);
        mViewPager = findViewById(R.id.pager);
        mTabLayout.addTab(mTabLayout.newTab().setText("List"));
        mTabLayout.addTab(mTabLayout.newTab().setText("Map"));
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), 2);
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.setAdapter(adapter);
        LatLng ll = new LatLng(41.91419, -88.30869);
        dataManager = DataManager.getDataManager(new RemoteDataSource(), new LocalDataSource());
        dataManager.getRemoteLocationData("bank", ll, 5000);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mViewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}