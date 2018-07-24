package com.jordangellatly.barfinder;

import com.jordangellatly.barfinder.models.Bar;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.HEAD;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface YelpClient {

    @GET("businesses/search")
    Call<Bar> listBars(@Header("Authorization") String apiKey, @Query("latitude") String latitude, @Query("longitude") String longitude, @Query("categories") String category);
}
