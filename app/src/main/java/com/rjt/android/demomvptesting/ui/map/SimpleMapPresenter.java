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
import com.rjt.android.demomvptesting.data.repository.local.LocalDataSource;
import com.rjt.android.demomvptesting.data.repository.remote.RemoteDataSource;

import java.util.List;

public class SimpleMapPresenter implements SimpleMapContract.IPresenter {
    private Context context;
    LocationManager locationManager;
    DataManager dataManager;
    String newQuery = "";
    GoogleMap googleMap;
    Location mLocation;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
            LatLng currentLoc = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            Log.i("CurrentLoc", currentLoc.latitude +" " + currentLoc.longitude);
            dataManager.getRemoteLocationData(newQuery, currentLoc, 5000);
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 16));
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };
    public SimpleMapPresenter(Context context) {
        this.context = context;
        dataManager = DataManager.getDataManager(new RemoteDataSource(), new LocalDataSource());
    }

    @Override
    public void start() {

    }

    @Override
    public void setOnMapReady(String query, GoogleMap googleMap) {
        Log.i("MapPresenter", "setOnMapReady: " + query);
        this.googleMap = googleMap;
        zoomToCurrentLocation(context, googleMap);
        newQuery = query;
    }


    public void zoomToCurrentLocation(Context context, GoogleMap googleMap) {
        try {
            if (ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) ==
                    PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_COARSE_LOCATION) ==
                            PackageManager.PERMISSION_GRANTED) {
                googleMap.setMyLocationEnabled(true);
                googleMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                Toast.makeText(context, "Error in permission", Toast.LENGTH_LONG).show();
            }
            LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 5, mLocationListener);
        } catch (SecurityException e) {
            e.printStackTrace();
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
