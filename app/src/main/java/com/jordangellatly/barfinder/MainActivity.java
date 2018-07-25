package com.jordangellatly.barfinder;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private static final String TAG = "MainActivity";

    private static final int MY_PERMISSIONS_REQUEST_COARSE_LOCATION = 1;

    @BindView(R.id.intro_text)
    TextView introText;
    @BindView(R.id.type_spinner)
    Spinner typeSpinner;
    @BindView(R.id.btn_search)
    Button btnSearch;

    private String category;
    private String latitude;
    private String longitude;

    private FusedLocationProviderClient fusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions();
        } else {
            getLocation();
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.bar_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
        typeSpinner.setOnItemSelectedListener(this);
    }

    private void requestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)) {

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setMessage("Please consider updating your location in order to use this app.")
                    .setTitle("Please enable location")
                    .setPositiveButton("Enable Location", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ActivityCompat.requestPermissions(MainActivity.this,
                                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                    MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
                        }
                    });
            AlertDialog dialog = builder.create();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                    MY_PERMISSIONS_REQUEST_COARSE_LOCATION);
        }
        return;
    }

    private void getLocation() {
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());
                }
            }
        });
    }

    @OnClick(R.id.btn_search)
    public void search() {
        Intent intent = new Intent(MainActivity.this, ListActivity.class);
        intent.putExtra("category", category);
        intent.putExtra("latitude", latitude);
        intent.putExtra("longitude", longitude);
        startActivity(intent);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
        category = parent.getItemAtPosition(i).toString().toLowerCase();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode) {
            case MY_PERMISSIONS_REQUEST_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    Toast.makeText(this, "Location Request denied", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }
}
