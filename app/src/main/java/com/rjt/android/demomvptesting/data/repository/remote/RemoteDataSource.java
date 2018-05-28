package com.rjt.android.demomvptesting.data.repository.remote;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.rjt.android.demomvptesting.R;
import com.rjt.android.demomvptesting.data.api.GoogleNearbyApiService;
import com.rjt.android.demomvptesting.data.api.RetrofitHelper;
import com.rjt.android.demomvptesting.data.model.bank.Example;
import com.rjt.android.demomvptesting.data.repository.IDataSource;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.support.constraint.Constraints.TAG;

public class RemoteDataSource implements IDataSource {
    private static final String GOOGLE_API_KEY = "AIzaSyBjzIx-qaBW1jIUECTJeHs7slsQiOnbzYc";
    @Override
    public void getRemoteLocationData(String type, LatLng ll, int radius) {
        GoogleNearbyApiService googleNearbyApiService = RetrofitHelper.getRetrofitHelper().getGoogleNearbyApiService();
        String location = String.valueOf(ll.latitude) + ","+String.valueOf(ll.longitude);
        Observable<Example> exampleObservable = googleNearbyApiService.getNearbyLocations(location, radius, type, GOOGLE_API_KEY);
        exampleObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Example>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(Example example) {
                        Log.i(TAG, "onNext: ");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete: ");
                    }
                });
    }
}
