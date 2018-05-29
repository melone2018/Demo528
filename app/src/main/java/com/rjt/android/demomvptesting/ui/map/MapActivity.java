package com.rjt.android.demomvptesting.ui.map;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.Toast;

import com.rjt.android.demomvptesting.ui.adapter.MyPagerAdapter;
import com.google.android.gms.maps.model.LatLng;
import com.rjt.android.demomvptesting.R;
import com.rjt.android.demomvptesting.data.repository.DataManager;
import com.rjt.android.demomvptesting.data.repository.local.LocalDataSource;
import com.rjt.android.demomvptesting.data.repository.remote.RemoteDataSource;

import de.greenrobot.event.EventBus;

public class MapActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener, MapActivityContract.IView{
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private DataManager dataManager;
    private SearchView editSearch;
    private MapActivityContract.IPresenter mPresenter;
    private String query;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        mTabLayout = findViewById(R.id.tablayout);
        editSearch = findViewById(R.id.searchview);
        mViewPager = findViewById(R.id.pager);
        mTabLayout.addTab(mTabLayout.newTab().setText("Map"));
        mTabLayout.addTab(mTabLayout.newTab().setText("List"));
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), 2, MapActivity.this);
        mTabLayout.addOnTabSelectedListener(this);
        mViewPager.setAdapter(adapter);
        LatLng ll = new LatLng(41.91419, -88.30869);
        dataManager = DataManager.getDataManager(new RemoteDataSource(), new LocalDataSource());
        //dataManager.getRemoteLocationData("bank", ll, 5000);
        editSearch.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                dataManager.getRemoteLocationData(query, ll, 5000);
                EventBus.getDefault().post(query);
                Toast.makeText(MapActivity.this, query, Toast.LENGTH_SHORT).show();
                editSearch.clearFocus();
                //DataManager.setExample(null);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                return false;
            }
        });
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


    @Override
    public void setPresenter(MapActivityContract.IPresenter presenter) {
        mPresenter = presenter;
    }
}
