package com.jordangellatly.barfinder.retrofit;

import com.jordangellatly.barfinder.models.Bar;
import com.jordangellatly.barfinder.models.Business;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface YelpClient {

    @GET("businesses/search")
    Call<Bar> listBars(@Header("Authorization") String apiKey, @Query("latitude") String latitude, @Query("longitude") String longitude, @Query("categories") String category);

    @GET("businesses/{id}")
    Call<Business> getBusinessDetails(@Header("Authorization") String apiKey, @Path("id") String businessId);
}
