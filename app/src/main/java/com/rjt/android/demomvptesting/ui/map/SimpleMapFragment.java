package com.rjt.android.demomvptesting.ui.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.rjt.android.demomvptesting.R;
import com.rjt.android.demomvptesting.data.repository.DataManager;

import de.greenrobot.event.EventBus;

import static com.google.android.gms.location.LocationServices.getFusedLocationProviderClient;

public class SimpleMapFragment extends Fragment implements OnMapReadyCallback, SimpleMapContract.IView {
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private LatLng latLng;
    private static SimpleMapContract.IPresenter simpleMapPresenter;
    private String query = "bank";
    private int REQUEST_FINE_LOCATION = 8809;
    private LocationRequest mLocationRequest;
    LocationCallback callback;

    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simplemap, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.simpelmap);
        mapFragment.getMapAsync(this);
        return view;
    }

    public static SimpleMapFragment newInstance(SimpleMapPresenter mapPresenter) {
        simpleMapPresenter = mapPresenter;
        SimpleMapFragment mapFragment = new SimpleMapFragment();
        return mapFragment;
    }

    public void onEvent(String query) {
        this.query = query;
        Log.i("MapFragment", "onEvent: " + query);
    }


    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        if (checkPermissions())
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
        googleMap.setMyLocationEnabled(true);
        //this.simpleMapPresenter.setOnMapReady(query, googleMap,latlng);
        Log.d("SimpleMapFragment", "onMapReady: " );
        startLocationUpdates();
       // Log.i("location", latLng.latitude + "");
//        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18));
//        this.simpleMapPresenter.setOnMapReady(query, googleMap,latLng);

    }

    @Override
    public void setPresenter(SimpleMapContract.IPresenter presenter) {
        this.simpleMapPresenter = presenter;
    }


    public void onLocationChanged(Location location) {
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
        Log.i("location", latLng.latitude + "");
        mFusedLocationProviderClient.removeLocationUpdates(callback);
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        this.simpleMapPresenter.setOnMapReady(query, googleMap,latLng);

    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            requestPermissions();
            return false;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_FINE_LOCATION);
    }

    protected void startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = new LocationRequest();
       // mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        //mLocationRequest.setFastestInterval(FASTEST_INTERVAL);

        // Create LocationSettingsRequest object using location request
        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder();
        builder.addLocationRequest(mLocationRequest);
        LocationSettingsRequest locationSettingsRequest = builder.build();

        // Check whether location settings are satisfied
        // https://developers.google.com/android/reference/com/google/android/gms/location/SettingsClient
        SettingsClient settingsClient = LocationServices.getSettingsClient(getActivity());
        settingsClient.checkLocationSettings(locationSettingsRequest);

        // new Google API SDK v11 uses getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mFusedLocationProviderClient = getFusedLocationProviderClient(getActivity());
        callback = new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult){
                super.onLocationResult(locationResult);
                onLocationChanged(locationResult.getLastLocation());
            }
        };
        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, callback, Looper.myLooper());

        mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        // do work here
                        onLocationChanged(locationResult.getLastLocation());
                    }
                },
                Looper.myLooper());
    }
}
