package com.example.wheeldeal;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.ResolvableApiException;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class addView extends AppCompatActivity {

    // Declare all the views
//    TextView txtCategory, txtBrand, txtModel, txtMilage, txtCapacity, txtDescription, txtPrice, txtArea;
    private TextView txtBack, textView15, textView16, textView17, textView18, textView19, textView28;

    TextView txtAddLocationD;
    TextView txtMyLocationD;
    Button btnLocationD;

    TextView modelFDB, descriptionFDB, areaFDB, priceFDB;

    private static final int REQUEST_CHECK_SETTINGS = 10001;
    private  LocationRequest locationRequest;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    favInfo favInfoObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_view);

        // Initialize all the views
        //btnBackButton = findViewById(R.id.btnBack);
        txtBack = findViewById(R.id.txtBack);
        textView15 = findViewById(R.id.textView15);
        textView16 = findViewById(R.id.textView16);
        textView17 = findViewById(R.id.textView17);
        textView18 = findViewById(R.id.textView18);
        textView19 = findViewById(R.id.textView19);
        textView28 = findViewById(R.id.textView28);
//        btnLocation = findViewById(R.id.button);
//        btnCall = findViewById(R.id.btncancel22);
//        btnChat = findViewById(R.id.button4);

        Intent intent = getIntent();
        if (intent != null) {
            String category = intent.getStringExtra("category");
            String brand = intent.getStringExtra("brand");
            int milage = intent.getIntExtra("milage", 0);
            int capacity = intent.getIntExtra("capacity", 0);
            String description = intent.getStringExtra("description");
            int price = intent.getIntExtra("price", 0);
            String area = intent.getStringExtra("area");

            // Populate the views with the ad details
            textView15.setText(brand); // Brand
            textView16.setText(area); // Location
            textView17.setText("Rs"); // Currency symbol
            textView18.setText(String.valueOf(price)); // Price
            textView19.setText("Description"); // Description label
            textView28.setText(description); // Description text

            TextView txtGetLocation = findViewById(R.id.txtAddLocationView);
            txtGetLocation.setText(area);
        }
        navigation();

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        //Map
        googleMapLocation();

        //Favourite
        addFavourite();

    }

    private void addFavourite(){
        modelFDB = findViewById(R.id.textView15);
        descriptionFDB = findViewById(R.id.textView28);
        areaFDB = findViewById(R.id.textView16);
        priceFDB = findViewById(R.id.textView18);


    }

    public void googleMapLocation() {
        txtAddLocationD = findViewById(R.id.txtAddLocationView);
        btnLocationD = findViewById(R.id.btnLocation);

        btnLocationD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(addView.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(addView.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
                        return;
                    }
                }

                if (isGPSEnabled()) {
                    FusedLocationProviderClient fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(addView.this);

                    LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000)
                            .setMinUpdateIntervalMillis(1000)
                            .build();

                    LocationCallback locationCallback = new LocationCallback() {
                        @Override
                        public void onLocationResult(@NonNull LocationResult locationResult) {
                            super.onLocationResult(locationResult);

                            if (locationResult != null && !locationResult.getLocations().isEmpty()) {
                                int index = locationResult.getLocations().size() - 1;
                                double latitude = locationResult.getLocations().get(index).getLatitude();
                                double longitude = locationResult.getLocations().get(index).getLongitude();
                                txtMyLocationD = findViewById(R.id.txtGetMyLocation);


                                if (txtMyLocationD != null) {
                                    txtMyLocationD.setText(String.format("%.6f, %.6f", latitude, longitude));

                                    String txtAdLocation = txtAddLocationD.getText().toString().trim();
                                    String txtMyLocation = txtMyLocationD.getText().toString().trim();
                                    if (txtAdLocation.isEmpty() || txtMyLocation.isEmpty()) {
                                        Toast.makeText(v.getContext(), "Your Location Not Found", Toast.LENGTH_SHORT).show();
                                    } else {
                                        getDirections(txtMyLocation, txtAdLocation);
                                    }
                                } else {
                                    Log.e("Location Error", "TextView txtMyLocationD is null");
                                }
                            }
                        }
                    };

                    fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper());
                } else {
                    turnOnGPS();
                }
            }
        });
    }



    private void getDirections(String from, String to) {
        try {
            Uri uri = Uri.parse("http://www.google.com/maps/dir/" + Uri.encode(from) + "/" + Uri.encode(to));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }


    private void turnOnGPS() {
        btnLocationD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                        .addLocationRequest(locationRequest);
                builder.setAlwaysShow(true);

                Task<LocationSettingsResponse> result = LocationServices.getSettingsClient(getApplicationContext())
                        .checkLocationSettings(builder.build());

                result.addOnCompleteListener(new OnCompleteListener<LocationSettingsResponse>() {
                    @Override
                    public void onComplete(@NonNull Task<LocationSettingsResponse> task) {

                        try {
                            LocationSettingsResponse response = task.getResult(ApiException.class);
                            Toast.makeText(addView.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                        } catch (ApiException e) {

                            switch (e.getStatusCode()) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                    try {
                                        ResolvableApiException resolvableApiException = (ResolvableApiException)e;
                                        resolvableApiException.startResolutionForResult(addView.this,REQUEST_CHECK_SETTINGS);
                                    } catch (IntentSender.SendIntentException ex) {
                                        ex.printStackTrace();
                                    }
                                    break;

                                case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                                    //Device does not have location
                                    break;
                            }
                        }
                    }
                });
            }
        });
    }

    private boolean isGPSEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager != null && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }



//    public void locationName(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = database.getReference("Ads");
//
//        TextView txtGetLocation = findViewById(R.id.txtGetLocationView);
//        if (area =! null){
//            String userID = area.getUid;
//
//            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Ads");
//
//            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()){
//                        String name = snapshot.child("location").getValue(String.class);
//                        txtGetLocation.setText(location);
//                    }else {
//                        txtGetLocation.setText("Location not Found");
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//
//
//    }

    public void navigation(){
        Button buttonCall =  findViewById(R.id.btnCall);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button buttonChat =  findViewById(R.id.btnChat);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(getApplicationContext(), chatsNew.class);
                startActivity(chatIntent);
            }
        });

        Button buttonLocation =  findViewById(R.id.btnLocation);
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent locationIntent = new Intent(getApplicationContext(), googleMap.class);
//                startActivity(locationIntent);

            }
        });





        ImageView btnBackbutton = findViewById(R.id.btnBack);

        btnBackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BackIntent = new Intent(getApplicationContext(), home.class);
                startActivity(BackIntent);
            }
        });

        TextView txtBackButton = findViewById(R.id.txtBack);

        txtBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Backtxtintent = new Intent(getApplicationContext(), home.class);
                startActivity(Backtxtintent);
            }
        });
    }


}