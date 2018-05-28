package com.rjt.android.demomvptesting.data.repository;

import com.google.android.gms.maps.model.LatLng;
import com.rjt.android.demomvptesting.data.repository.local.LocalDataSource;
import com.rjt.android.demomvptesting.data.repository.remote.RemoteDataSource;

public class DataManager implements IDataSource{
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;
    private static DataManager dataManager = null;
    private DataManager(RemoteDataSource remoteDataSource, LocalDataSource localDataSource){
        this.remoteDataSource = remoteDataSource;
        this.localDataSource = localDataSource;
    }

    public static DataManager getDataManager(RemoteDataSource remoteDataSource, LocalDataSource localDataSource){
        if(dataManager==null) {
            dataManager = new DataManager(remoteDataSource, localDataSource);
        }
        return dataManager;
    }



    @Override
    public void getRemoteLocationData(String type, LatLng ll, int radius) {
        remoteDataSource.getRemoteLocationData(type, ll, radius);
    }
}
