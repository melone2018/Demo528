package com.rjt.android.demomvptesting.ui.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyLocationAdapter extends RecyclerView.Adapter<MyLocationAdapter.LocationHolder> {
    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_layout, parent, false);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class LocationHolder extends RecyclerView.ViewHolder {
        public LocationHolder(View itemView) {
            super(itemView);
        }
    }
}
