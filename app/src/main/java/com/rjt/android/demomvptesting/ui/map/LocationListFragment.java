package com.rjt.android.demomvptesting.ui.map;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rjt.android.demomvptesting.R;
import com.rjt.android.demomvptesting.data.repository.DataManager;
import com.rjt.android.demomvptesting.ui.adapter.MyLocationAdapter;

public class LocationListFragment extends Fragment {
    RecyclerView mRecyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_location_list, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerview);
        MyLocationAdapter locationAdapter = new MyLocationAdapter(DataManager.getExample().getResults(), getActivity());
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(), layoutManager.getOrientation());
        return view;
    }
    public static LocationListFragment newInstance(){
        LocationListFragment listFragment = new LocationListFragment();
        return listFragment;
    }
}
