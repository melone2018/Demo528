package com.rjt.android.demomvptesting.ui.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rjt.android.demomvptesting.R;
import com.rjt.android.demomvptesting.data.model.bank.Result;

import java.util.List;

public class MyLocationAdapter extends RecyclerView.Adapter<MyLocationAdapter.LocationHolder> {
    List<Result> mResults;
    Context mContext;

    public MyLocationAdapter(List<Result> results, Context context) {
        mResults = results;
        mContext = context;
    }

    @NonNull
    @Override
    public LocationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.location_item_layout, parent, false);
        return new LocationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LocationHolder holder, int position) {
        holder.nameTv.setText(mResults.get(position).getName());
        holder.locationTv.setText(mResults.get(position).getGeometry().getLocation().getLat() + " " + mResults.get(position).getGeometry().getLocation().getLng());
        Glide.with(mContext)
                .load(mResults.get(position).getIcon())
                .into(holder.iconIv);
    }

    @Override
    public int getItemCount() {
        return mResults.size();
    }

    public class LocationHolder extends RecyclerView.ViewHolder {
        TextView nameTv, locationTv;
        ImageView iconIv;
        public LocationHolder(View itemView) {
            super(itemView);
            nameTv = itemView.findViewById(R.id.name);
            locationTv = itemView.findViewById(R.id.location);
            iconIv = itemView.findViewById(R.id.imageIcon);
        }
    }
}
