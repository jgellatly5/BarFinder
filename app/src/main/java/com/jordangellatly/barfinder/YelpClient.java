package com.jordangellatly.barfinder;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface YelpClient {

    static final String API_KEY = "mUk4y0zvNI7RJ8coBuobzsTT8w0bGUMmauOciscQ-lB2SQfe7zZdco8FnlqU8BH1blaEfJJv_2TCVJh0excsjuDrdiyLtNSVecvccAk43xim0N2jyQY_Uxjj6C8eWHYx";

    @Headers("Authorization: Bearer " + API_KEY)
    @GET("businesses/search")
    Call<List<Bar>> listBars(@Query("categories") String category);
}
