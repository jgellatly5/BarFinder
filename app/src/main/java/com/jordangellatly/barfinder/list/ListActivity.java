package com.jordangellatly.barfinder.list;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jordangellatly.barfinder.R;
import com.jordangellatly.barfinder.models.Business;
import com.jordangellatly.barfinder.retrofit.YelpClient;
import com.jordangellatly.barfinder.detail.DetailActivity;
import com.jordangellatly.barfinder.models.Bar;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ListActivity extends AppCompatActivity implements BarListAdapter.BarListAdapterListener {

    private static final String TAG = "ListActivity";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Retrofit retrofit;
    private YelpClient yelpClient;

    private String category;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        ButterKnife.bind(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressBar.setVisibility(ProgressBar.VISIBLE);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.networkInterceptors().add(httpLoggingInterceptor);
        OkHttpClient okHttpClient = builder.build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(getString(R.string.BASE_URL))
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        yelpClient = retrofit.create(YelpClient.class);

        category = getIntent().getStringExtra("category");
        latitude = getIntent().getStringExtra("latitude");
        longitude = getIntent().getStringExtra("longitude");

        Call<Bar> call = yelpClient.listBars(getString(R.string.API_KEY), latitude, longitude, category);
        call.enqueue(new Callback<Bar>() {
            @Override
            public void onResponse(Call<Bar> call, Response<Bar> response) {
                Bar bar = response.body();
                List<Business> businesses = bar.getBusinesses();
                ArrayList<String> businessNames = new ArrayList<>();
                for (Business singleBusiness : businesses) {
                    businessNames.add(singleBusiness.getName());
                }

                BarListAdapter barListAdapter = new BarListAdapter(businesses, ListActivity.this);
                recyclerView.setAdapter(barListAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(ListActivity.this));
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Bar> call, Throwable t) {
                Toast.makeText(ListActivity.this, "Something went wrong.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBarSelected(Business business) {
        Intent intent = new Intent(ListActivity.this, DetailActivity.class);
        intent.putExtra("businessId", business.getId());
        startActivity(intent);
    }
}
