package com.jordangellatly.barfinder;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.jordangellatly.barfinder.models.Bar;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";
    
    @BindView(R.id.intro_text)
    TextView introText;
    @BindView(R.id.type_spinner)
    Spinner typeSpinner;
    @BindView(R.id.btn_search)
    Button btnSearch;

    private static final String BASE_URL = "https://api.yelp.com/v3/";

    private static final String API_KEY = "Bearer mUk4y0zvNI7RJ8coBuobzsTT8w0bGUMmauOciscQ-lB2SQfe7zZdco8FnlqU8BH1blaEfJJv_2TCVJh0excsjuDrdiyLtNSVecvccAk43xim0N2jyQY_Uxjj6C8eWHYx";

    private static final String latitude = "37.801459";
    private static final String longitude = "-122.26525579999998";
    
    private String category;

    private Retrofit retrofit;
    private YelpClient yelpClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Stetho.initializeWithDefaults(this);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bar_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(this);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        OkHttpClient okHttpClient = builder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        yelpClient = retrofit.create(YelpClient.class);
    }

    @OnClick(R.id.btn_search)
    public void search() {
        Call<Bar> call = yelpClient.listBars(API_KEY, latitude, longitude, category);
        call.enqueue(new Callback<Bar>() {
            @Override
            public void onResponse(Call<Bar> call, Response<Bar> response) {


            }

            @Override
            public void onFailure(Call<Bar> call, Throwable t) {
                Log.e(TAG, "onFailure: " + t.getMessage());

            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        category = parent.getItemAtPosition(i).toString().toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
