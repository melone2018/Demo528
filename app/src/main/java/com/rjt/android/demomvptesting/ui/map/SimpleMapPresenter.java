package com.rjt.android.demomvptesting.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.rjt.android.demomvptesting.data.model.bank.Result;
import com.rjt.android.demomvptesting.data.repository.DataManager;
import com.rjt.android.demomvptesting.data.repository.local.LocalDataSource;
import com.rjt.android.demomvptesting.data.repository.remote.RemoteDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class SimpleMapPresenter implements SimpleMapContract.IPresenter {
    private Context context;
    private static final String TAG = "SimpleMapPresenter";
    private static final String FINE_LOCATION = android.Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;

    LocationManager locationManager;
    DataManager dataManager;
    Location mlocation;
    String newQuery = "";
    GoogleMap googleMap;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            //your code here
            mlocation = location;
            Log.i("getLoc", "Location: " + mlocation.getLatitude() + " " + mlocation.getLongitude());
            LatLng currentLoc = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
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
        getDeviceLocation(googleMap);
        newQuery = query;
        //Log.i("LOCATION", mlocation.getLatitude() + " " + mlocation.getLongitude());
//        LatLng currentLoc = new LatLng(mlocation.getLatitude(),mlocation.getLongitude());
//        dataManager.getRemoteLocationData(query, currentLoc, 5000);
    }


    private void getDeviceLocation(GoogleMap googleMap) {
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
            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 50, 5, mLocationListener);
            setMarkers();
//            if(mlocation!=null)
//                return new LatLng(mlocation.getLatitude(), mlocation.getLongitude());

        } catch (SecurityException e) {
            e.printStackTrace();
        }
       // return new LatLng(mlocation.getLatitude(), mlocation.getLongitude());
        //return null;
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
            Log.i("NameBank", name);
            //Bitmap bitmap = getBitmapFromURL(iconUrl);
            MarkerOptions options = new MarkerOptions().title(name).position(ll);
            Marker m = googleMap.addMarker(options);
        }
    }

    public Bitmap getBitmapFromURL(String imageUrl) {
//        try {
//            URL url = new URL(imageUrl);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setDoInput(true);
//            connection.connect();
//            InputStream input = connection.getInputStream();
//            Bitmap myBitmap = BitmapFactory.decodeStream(input);
//            return myBitmap;
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }


        try {
            Bitmap chefBitmap = Glide.with(context)
                    .asBitmap()
                    .load(imageUrl)
                    .submit()
                    .get();
            return chefBitmap;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }

}
