package com.rjt.android.demomvptesting.ui.map;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
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

import java.util.List;

public class MapActivityPresenter implements MapActivityContract.IPresenter {
    GoogleMap mGoogleMap;
    private Context context;
    LocationManager mLocationManager;
    Location mLocation;
    DataManager mDataManager;
    String newQuery = "";
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            mLocation = location;
            LatLng currentLoc = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
            mDataManager.getRemoteLocationData(newQuery, currentLoc, 5000);
            mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLoc, 16));
        }
    };

    @Override
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
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 5, (android.location.LocationListener) mLocationListener);

        } catch (SecurityException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void getMapReady(Context mContext, String query, GoogleMap googleMap) {
        this.mGoogleMap = googleMap;
        this.context = mContext;
        getDeviceLocation(mGoogleMap);
        newQuery = query;
    }

    @Override
    public void start() {

    }

    private void getDeviceLocation(GoogleMap googleMap){
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
            mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            if (mLocationManager != null) {
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 5, (android.location.LocationListener) mLocationListener);
            }
            setMarkers();
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
            Marker m = mGoogleMap.addMarker(options);
        }
    }


}
