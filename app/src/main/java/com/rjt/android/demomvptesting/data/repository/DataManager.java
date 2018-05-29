package com.rjt.android.demomvptesting.data.repository;

import com.google.android.gms.maps.model.LatLng;
import com.rjt.android.demomvptesting.data.model.bank.Example;
import com.rjt.android.demomvptesting.data.repository.local.LocalDataSource;
import com.rjt.android.demomvptesting.data.repository.remote.RemoteDataSource;

public class DataManager implements IDataSource{
    private RemoteDataSource remoteDataSource;
    private LocalDataSource localDataSource;

    public static Example getExample() {
        return example;
    }

    public static void setExample(Example example2) {
        example = example2;
    }

    private static Example example=null;
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
