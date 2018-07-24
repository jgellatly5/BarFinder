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

    String API_KEY = "mUk4By0zvNI7RJ8coBuobzsTT8w0bGUMmauOciscQ-lB2SQfe7zZdco8FnlqU8BH1blaEfJJv_2TCVJh0excsjuDrdiyLtNSVecvccAk43xim0N2jyQY_Uxjj6C8eWHYx";

//    @Headers("Authorization: Bearer mUk4By0zvNI7RJ8coBuobzsTT8w0bGUMmauOciscQ-lB2SQfe7zZdco8FnlqU8BH1blaEfJJv_2TCVJh0excsjuDrdiyLtNSVecvccAk43xim0N2jyQY_Uxjj6C8eWHYx")
    @GET("businesses/search")
    Call<Bar> listBars(@Header("Authorization") String apiKey, @Query("latitude") String latitude, @Query("longitude") String longitude, @Query("categories") String category);
}
