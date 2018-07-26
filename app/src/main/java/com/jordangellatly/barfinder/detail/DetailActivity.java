package com.jordangellatly.barfinder.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.jordangellatly.barfinder.R;
import com.jordangellatly.barfinder.models.Business;
import com.jordangellatly.barfinder.retrofit.RetrofitProvider;
import com.jordangellatly.barfinder.retrofit.YelpClient;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailActivity extends AppCompatActivity {

    private static final String TAG = "DetailActivity";

    @BindView(R.id.detail_image)
    ImageView detailImage;
    @BindView(R.id.business_name)
    TextView businessName;
    @BindView(R.id.phone_number)
    TextView phoneNumber;
    @BindView(R.id.business_address)
    TextView businessAddress;
    @BindView(R.id.business_rating)
    TextView businessRating;
    @BindView(R.id.hours_text)
    TextView hoursText;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private Retrofit retrofit;
    private YelpClient yelpClient;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        progressBar.setVisibility(ProgressBar.VISIBLE);

        retrofit = RetrofitProvider.getInstance(DetailActivity.this);
        yelpClient = retrofit.create(YelpClient.class);

        String businessId = getIntent().getStringExtra("businessId");
        Call<Business> call = yelpClient.getBusinessDetails(getString(R.string.API_KEY), businessId);
        call.enqueue(new Callback<Business>() {
            @Override
            public void onResponse(Call<Business> call, Response<Business> response) {
                Business business = response.body();
                Picasso.get().load(business.getImageUrl()).into(detailImage);
                businessName.setText(business.getName());
                phoneNumber.setText(business.getDisplayPhone());
                businessAddress.setText(business.getLocation().getAddress1());
                businessRating.setText(business.getRating().toString());
                progressBar.setVisibility(ProgressBar.INVISIBLE);
            }

            @Override
            public void onFailure(Call<Business> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "Something is not working.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
