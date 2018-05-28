package com.rjt.android.demomvptesting.data.repository;

import com.google.android.gms.maps.model.LatLng;

public interface IDataSource {
    interface LocalCallBack{

    }

    interface RemoteCallBack{

    }

    void getRemoteLocationData(String type, LatLng ll, int radius);

}
