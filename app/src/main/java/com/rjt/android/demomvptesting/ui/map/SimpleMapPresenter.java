package com.rjt.android.demomvptesting.ui.map;

import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rjt.android.demomvptesting.data.model.bank.Result;
import com.rjt.android.demomvptesting.data.repository.DataManager;
import com.rjt.android.demomvptesting.data.repository.IDataSource;
import com.rjt.android.demomvptesting.data.repository.local.LocalDataSource;
import com.rjt.android.demomvptesting.data.repository.remote.RemoteDataSource;

import java.util.List;

public class SimpleMapPresenter implements SimpleMapContract.IPresenter {
    private Context context;
    DataManager dataManager;
    String newQuery = "";
    GoogleMap googleMap;
    Location mLocation;

    public SimpleMapPresenter(Context context) {
        this.context = context;
        dataManager = DataManager.getDataManager(new RemoteDataSource(), new LocalDataSource());
    }

    @Override
    public void start() {

    }

    @Override
    public void setOnMapReady(String query, GoogleMap googleMap, LatLng ll) {
        Log.i("MapPresenter", "setOnMapReady: " + query);
        this.googleMap = googleMap;
        googleMap.clear();
        dataManager.getRemoteLocationData(query, ll, 5000, new IDataSource.RemoteCallBack() {
            @Override
            public void onSuccess() {
                Log.i("drawMarker", dataManager.getExample().getResults().size()+"");
//                setMarkers();
            }
        });
        if(dataManager.getExample()!=null) {
            Log.i("MapPresenter", dataManager.getExample().getResults().size() + "");
            setMarkers();
        }
    }

    void setMarkers(){
        List<Result> results = DataManager.getExample().getResults();
        for(int i = 0; i < results.size(); i++){
            Result res = results.get(i);
            String iconUrl = res.getIcon();
            double lat = res.getGeometry().getLocation().getLat();
            double lng = res.getGeometry().getLocation().getLng();
            LatLng ll = new LatLng(lat, lng);
            String name = res.getName();
            MarkerOptions options = new MarkerOptions().title(name).position(ll);
            Marker m = googleMap.addMarker(options);
        }
    }

}
