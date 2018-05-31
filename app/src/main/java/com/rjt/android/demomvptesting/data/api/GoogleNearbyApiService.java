package com.rjt.android.demomvptesting.data.api;

import com.rjt.android.demomvptesting.data.model.bank.Example;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GoogleNearbyApiService {
    @GET("place/nearbysearch/json")
    Observable<Example> getNearbyLocations(@Query("location") String location,
                                           @Query("radius") int radius,
                                           @Query("types") String types,
                                           @Query("key") String key);
}
