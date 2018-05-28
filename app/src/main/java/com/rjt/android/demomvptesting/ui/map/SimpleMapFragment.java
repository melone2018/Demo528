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

public class SimpleMapFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private SimpleMapPresenter simpleMapPresenter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_simplemap, container, false);
        mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.simpelmap);
        mapFragment.getMapAsync(this);
        return view;
    }

    public static SimpleMapFragment newInstance(){
        SimpleMapFragment mapFragment = new SimpleMapFragment();
        return mapFragment;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        Log.d("SimpleMapFragment", "onMapReady: ");
    }
}
