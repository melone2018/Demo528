package com.rjt.android.demomvptesting.data.api;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    private GoogleNearbyApiService googleNearbyApiService;
    private static RetrofitHelper retrofitHelper = null;
    private final String GOOGLE_NEARBY_BASE_URL = "https://maps.googleapis.com/maps/api/";
    public static RetrofitHelper getRetrofitHelper(){
        if(retrofitHelper==null){
            retrofitHelper = new RetrofitHelper();
        }
        return retrofitHelper;
    }

    private RetrofitHelper(){
        buildRetrofit();
    }

    public GoogleNearbyApiService getGoogleNearbyApiService() {
        return googleNearbyApiService;
    }

    private void buildRetrofit() {
        Retrofit retrofit = new retrofit2.Retrofit
                .Builder()
                .baseUrl(GOOGLE_NEARBY_BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).build();
        this.googleNearbyApiService = retrofit.create(GoogleNearbyApiService.class);
    }

}
