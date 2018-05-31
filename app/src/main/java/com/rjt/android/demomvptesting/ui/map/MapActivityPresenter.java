package com.rjt.android.demomvptesting.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.widget.Toast;

import com.google.android.gms.location.LocationListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rjt.android.demomvptesting.data.model.bank.Result;
import com.rjt.android.demomvptesting.data.repository.DataManager;
import com.rjt.android.demomvptesting.data.repository.IDataSource;

import java.util.List;

public class MapActivityPresenter implements MapActivityContract.IPresenter {
    GoogleMap mGoogleMap;
    LocationManager mLocationManager;
    Location mLocation;
    DataManager mDataManager;
    String newQuery = "";
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
            LatLng currentLoc = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            mDataManager.getRemoteLocationData(newQuery, currentLoc, 5000, new IDataSource.RemoteCallBack() {
                @Override
                public void onSuccess() {
                    //DataManager.setExample();
                }
            });
            //mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 16));
        }
    };
    private Context context;



    @Override
    public void getMapReady(Context mContext, String query, GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        this.context = mContext;
        newQuery = query;
    }

    @Override
    public void start() {

    }


}
