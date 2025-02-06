package com.example.wheeldeal;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class createnewadd extends AppCompatActivity {

    ImageView addImage1, addImage2, addImage3;
    ActivityResultLauncher<Intent> resultLuncher1, resultLuncher2, resultLuncher3;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    Button btnChooseLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createnewadd);


        cmbbox();
        imgeAddClass();


        btnChooseLocation = findViewById(R.id.btnChooseLocation);
        btnChooseLocation.setOnClickListener(v -> requestLocationPermission());
    }

    public void cmbbox() {

        Spinner Category = findViewById(R.id.cmbVehicleCategory);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.vehicleCategory, android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category.setAdapter(adapter1);


        Spinner Brand = findViewById(R.id.cmbVehicleBrand);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.vehicleBrand, android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Brand.setAdapter(adapter2);
    }

    public void imgeAddClass() {
        addImage1 = findViewById(R.id.imgAdd1);
        addImage2 = findViewById(R.id.imgAdd2);
        addImage3 = findViewById(R.id.imgAdd3);

        // Image Selection Logic
        resultLuncher1 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        addImage1.setImageURI(imageUri);
                    } else {
                        Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        addImage1.setOnClickListener(v -> displayImg1());

        resultLuncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        addImage2.setImageURI(imageUri);
                    } else {
                        Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        addImage2.setOnClickListener(v -> displayImg2());

        resultLuncher3 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        Uri imageUri = result.getData().getData();
                        addImage3.setImageURI(imageUri);
                    } else {
                        Toast.makeText(this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        addImage3.setOnClickListener(v -> displayImg3());
    }

    // Image Selection Methods
    private void displayImg1() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLuncher1.launch(intent);
    }

    private void displayImg2() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLuncher2.launch(intent);
    }

    private void displayImg3() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLuncher3.launch(intent);
    }


    private void requestLocationPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            openMap();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openMap();
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void openMap() {
        Uri gmmIntentUri = Uri.parse("geo:0,0?q=my+location");
        Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
        mapIntent.setPackage("com.google.android.apps.maps");

        if (mapIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(mapIntent);
        } else {
            Toast.makeText(this, "Google Maps not installed", Toast.LENGTH_SHORT).show();
        }
    }
}
