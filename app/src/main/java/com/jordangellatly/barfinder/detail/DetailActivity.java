package com.jordangellatly.barfinder.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.jordangellatly.barfinder.R;
import com.jordangellatly.barfinder.models.Business;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Business business = Parcels.unwrap(getIntent().getParcelableExtra("bar"));
        Picasso.get().load(business.getImageUrl()).into(detailImage);
        businessName.setText(business.getName());
        phoneNumber.setText(business.getDisplayPhone());
        businessAddress.setText(business.getLocation().getAddress1());
        businessRating.setText(business.getRating().toString());
    }
}
