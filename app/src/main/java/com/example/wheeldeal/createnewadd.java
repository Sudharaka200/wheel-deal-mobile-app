package com.example.wheeldeal;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.health.connect.datatypes.ExerciseRoute;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import com.google.android.gms.tasks.Task;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

public class createnewadd extends AppCompatActivity {



    ImageView addImage1, addImage2, addImage3;
    ActivityResultLauncher<Intent> resultLauncher1, resultLauncher2, resultLauncher3;
    Uri imageUri1, imageUri2, imageUri3;

    Button btnGetLocation;
    private static final int REQUEST_CHECK_SETTINGS = 10001;

    private Spinner categoryEdt, brandEdt;
    private EditText modelEdt, milageEdt, capacityEdt, descriptionEdt, priceEdt, locationEdt;
    private Button btnPost;
    private  LocationRequest locationRequest;

    FirebaseAuth auth;
    TextView emailCheck;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewadd);

        //user check
        auth = FirebaseAuth.getInstance();
        emailCheck = findViewById(R.id.getloginEmail);
        user = auth.getCurrentUser();
        if (user == null){

        }
        else {
            emailCheck.setText(user.getEmail());
        }

        // Initialize UI elements
        categoryEdt = findViewById(R.id.cmbVehicleCategory);
        brandEdt = findViewById(R.id.cmbVehicleBrand);
        modelEdt = findViewById(R.id.txtModel);
        milageEdt = findViewById(R.id.txtMilage);
        capacityEdt = findViewById(R.id.txtCapacity);
        descriptionEdt = findViewById(R.id.txtDescription);
        priceEdt = findViewById(R.id.txtPrice);
        locationEdt = findViewById(R.id.txtLocation);
        btnPost = findViewById(R.id.btnPostAd);
        emailCheck = findViewById(R.id.getloginEmail);

        addImage1 = findViewById(R.id.imgAdd1);
        addImage2 = findViewById(R.id.imgAdd2);
        addImage3 = findViewById(R.id.imgAdd3);

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(5000);
        locationRequest.setFastestInterval(2000);

        cmbbox();  // Initialize the spinners
        setupImagePickers();  // Setup image selection

        btnPost.setOnClickListener(v -> {
            insertTxtDB();
        });


        btnGetLocation =  findViewById(R.id.btnChooseLocation);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(createnewadd.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101); // Request permission if not granted
                        return;
                    }

                    if (isGPSEnabled()) {
                        LocationServices.getFusedLocationProviderClient(createnewadd.this)
                                .requestLocationUpdates(new LocationRequest(), new LocationCallback() {
                                    @Override
                                    public void onLocationResult(@NonNull LocationResult locationResult) {
                                        super.onLocationResult(locationResult);

                                        LocationServices.getFusedLocationProviderClient(createnewadd.this)
                                                .removeLocationUpdates(this);

                                        if (locationResult != null && locationResult.getLocations().size() > 0) {
                                            int index = locationResult.getLocations().size() - 1;
                                            double latitude = locationResult.getLocations().get(index).getLatitude();
                                            double longitude = locationResult.getLocations().get(index).getLongitude();

                                            TextView txtLocation = findViewById(R.id.txtLocation);
                                            if (txtLocation != null) {
                                                txtLocation.setText("Latitude: " + latitude + ", Longitude: " + longitude);
                                            } else {
                                                Log.e("Location Error", "TextView is null");
                                            }
                                        }
                                    }
                                }, Looper.getMainLooper());
                    } else {
                        turnOnGPS();
                    }
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
                }
            }
        });
    }

    private void turnOnGPS() {
        btnGetLocation = findViewById(R.id.btnChooseLocation);
        btnGetLocation.setOnClickListener(new View.OnClickListener() {
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
                            Toast.makeText(createnewadd.this, "GPS is already tured on", Toast.LENGTH_SHORT).show();

                        } catch (ApiException e) {

                            switch (e.getStatusCode()) {
                                case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:

                                    try {
                                        ResolvableApiException resolvableApiException = (ResolvableApiException)e;
                                        resolvableApiException.startResolutionForResult(createnewadd.this,REQUEST_CHECK_SETTINGS);
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



    private void uploadMultipleImages(String adKey) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("ads_images");

        Uri[] imageUris = {imageUri1, imageUri2, imageUri3};

        for (int i = 0; i < imageUris.length; i++) {
            if (imageUris[i] == null) continue;

            String imageName = System.currentTimeMillis() + "_img" + (i + 1) + ".jpg";
            StorageReference imageRef = storageReference.child(imageName);

            imageRef.putFile(imageUris[i])
                    .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> saveImageUrlToDatabase(adKey, uri.toString())))
                    .addOnFailureListener(e ->
                            Toast.makeText(createnewadd.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        }
    }

    private void saveImageUrlToDatabase(String adKey, String imageUrl) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("ads").child(adKey).child("images");
        String imageKey = dbRef.push().getKey();
        dbRef.child(imageKey).setValue(imageUrl)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(createnewadd.this, "Succesfull posted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(createnewadd.this, "Failed to save image URL.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void insertTxtDB() {
        String categoryD = categoryEdt.getSelectedItem().toString().trim();
        String brandD = brandEdt.getSelectedItem().toString().trim();
        String modelD = modelEdt.getText().toString().trim();
        int mileageD = Integer.parseInt(milageEdt.getText().toString().trim());
        int capacityD = Integer.parseInt(capacityEdt.getText().toString().trim());
        String descriptionD = descriptionEdt.getText().toString();
        int priceD = Integer.parseInt(priceEdt.getText().toString().trim());

        String locationD = locationEdt.getText().toString();
        String emailD = emailCheck.getText().toString();

        String locationD = locationEdt.getText().toString().trim();


        if (categoryD.isEmpty() || brandD.isEmpty() || mileageD == 0 || capacityD == 0 || descriptionD.isEmpty() || priceD == 0 || locationD.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("ads");
        String key = dbRef.push().getKey();

        HashMap<String, Object> dbHashMap = new HashMap<>();
        dbHashMap.put("category", categoryD);
        dbHashMap.put("brand", brandD);
        dbHashMap.put("model", modelD);
        dbHashMap.put("milage", mileageD);
        dbHashMap.put("capacity", capacityD);
        dbHashMap.put("description", descriptionD);
        dbHashMap.put("price", priceD);
        dbHashMap.put("location", locationD);
        dbHashMap.put("email", emailD);

        dbRef.child(key).setValue(dbHashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
//                Toast.makeText(createnewadd.this, "Ad posted successfully", Toast.LENGTH_SHORT).show();
                uploadMultipleImages(key);

                Button btnsuccesfull = findViewById(R.id.btnPostAd);
                btnsuccesfull.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent adSuccesfullIntent = new Intent(getApplicationContext(), postSuccesful.class);
                        startActivity(adSuccesfullIntent);
                    }
                });

            } else {
                Toast.makeText(createnewadd.this, "Ad post failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cmbbox() {
        // Vehicle Category Spinner
        Spinner Category = findViewById(R.id.cmbVehicleCategory);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this, R.array.vehicleCategory, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category.setAdapter(adapter1);

        // Vehicle Brand Spinner
        Spinner Brand = findViewById(R.id.cmbVehicleBrand);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.vehicleBrand, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Brand.setAdapter(adapter2);
    }

    private void setupImagePickers() {
        resultLauncher1 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imageUri1 = result.getData().getData();
                        addImage1.setImageURI(imageUri1);
                    }
                });
        addImage1.setOnClickListener(v -> displayImagePicker(resultLauncher1));

        resultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imageUri2 = result.getData().getData();
                        addImage2.setImageURI(imageUri2);
                    }
                });
        addImage2.setOnClickListener(v -> displayImagePicker(resultLauncher2));

        resultLauncher3 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        imageUri3 = result.getData().getData();
                        addImage3.setImageURI(imageUri3);
                    }
                });
        addImage3.setOnClickListener(v -> displayImagePicker(resultLauncher3));
    }

    private void displayImagePicker(ActivityResultLauncher<Intent> launcher) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        launcher.launch(intent);
    }

}