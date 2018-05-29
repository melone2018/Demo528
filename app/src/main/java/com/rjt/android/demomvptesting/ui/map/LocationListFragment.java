package com.rjt.android.demomvptesting.ui.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rjt.android.demomvptesting.R;

public class LocationListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);
        return view;
    }
    public static LocationListFragment newInstance(){
        LocationListFragment listFragment = new LocationListFragment();
        return listFragment;
    }
}
