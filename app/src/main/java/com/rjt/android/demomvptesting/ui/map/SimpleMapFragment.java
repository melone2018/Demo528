package com.rjt.android.demomvptesting.ui.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.rjt.android.demomvptesting.R;
import com.rjt.android.demomvptesting.data.repository.DataManager;

import de.greenrobot.event.EventBus;

public class SimpleMapFragment extends Fragment implements OnMapReadyCallback, SimpleMapContract.IView {
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private static SimpleMapContract.IPresenter simpleMapPresenter;
    private String query="bank";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_simplemap, container, false);
        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.simpelmap);
        mapFragment.getMapAsync(this);
        return view;
    }

    public static SimpleMapFragment newInstance(SimpleMapPresenter mapPresenter){
        simpleMapPresenter = mapPresenter;
        SimpleMapFragment mapFragment = new SimpleMapFragment();
        return mapFragment;
    }

    public void onEvent(String query){
        this.query = query;
        Log.i("MapFragment", "onEvent: " +  query);
        //googleMap.clear();
       // DataManager.setExample(null);
        simpleMapPresenter.setOnMapReady(query, googleMap);
    }


    @Override
    public void onStart(){
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause(){
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
       // this.simpleMapPresenter.setOnMapReady(query, googleMap);
        Log.d("SimpleMapFragment", "onMapReady: ");

    }

    @Override
    public void setPresenter(SimpleMapContract.IPresenter presenter) {
        this.simpleMapPresenter = presenter;
    }
}
