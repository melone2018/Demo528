package com.rjt.android.demomvptesting.ui.map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.location.LocationProvider;
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

public class SimpleMapFragment extends Fragment implements OnMapReadyCallback, SimpleMapContract.IView, LocationListener {
    private GoogleMap googleMap;
    private SupportMapFragment mapFragment;
    private LatLng latLng;
    private static SimpleMapContract.IPresenter simpleMapPresenter;
    private String query = "bank";
    private int REQUEST_FINE_LOCATION = 8809;
    private LocationRequest mLocationRequest;
    LocationCallback callback;
    LocationManager mLocationManager;
    String newQuery = "";
    private long UPDATE_INTERVAL = 10 * 1000;  /* 10 secs */
    private long FASTEST_INTERVAL = 2000; /* 2 sec */
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_simplemap, container, false);
        LocationUtils();
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.simpelmap);
        mapFragment.getMapAsync(this);
        return view;
    }

    public static SimpleMapFragment newInstance(SimpleMapPresenter mapPresenter) {
        simpleMapPresenter = mapPresenter;
        SimpleMapFragment mapFragment = new SimpleMapFragment();
//        Log.i("ManageSize", DataManager.getExample().getResults().size()+"");
        return mapFragment;
    }

    public void onEvent(String query) {
        this.query = query;
        this.newQuery = query;
        Log.i("MapFragment", "onEvent: " + query);
    }
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
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
        Log.d("SimpleMapFragment", "onMapReady: ");
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
        this.simpleMapPresenter.setOnMapReady(this.newQuery, googleMap, latLng);
    }

    @Override
    public void setPresenter(SimpleMapContract.IPresenter presenter) {
        this.simpleMapPresenter = presenter;
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

    @Override
    public void onLocationChanged(Location location) {
        Log.i("simpleMapFragment", location.getLatitude() + "");
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
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

    public void LocationUtils() {
        Context context = getActivity();
        // Get the location manager
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        // Define the criteria how to select the locatioin provider -> use
        // default
        Criteria criteria = new Criteria();
        String provider = mLocationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = mLocationManager.getLastKnownLocation(provider);

        // Initialize the location fields
        if (location != null) {
            onLocationChanged(location);
        } else {
        }
    }
}
