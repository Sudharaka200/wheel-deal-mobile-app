package com.example.wheeldeal;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;


public class createnewadd extends AppCompatActivity {

    ImageView addImage1;
    ImageView addImage2;
    ImageView addImage3;
    ActivityResultLauncher<Intent> resultLuncher1;
    ActivityResultLauncher<Intent> resultLuncher2;
    ActivityResultLauncher<Intent> resultLuncher3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createnewadd);

        //Combobox
        cmbbox();

        //Images Add for create new add
        addImage1 = findViewById(R.id.imgAdd1);
        addImage2 = findViewById(R.id.imgAdd2);
        addImage3 = findViewById(R.id.imgAdd3);
        imgeAddClass();



    }
    public void cmbbox(){
        //Vehicle Category
        Spinner Category = findViewById(R.id.cmbVehicleCategory);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.vehicleCategory, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Category.setAdapter(adapter1);

        //Vehicle Brand
        Spinner Brand = findViewById(R.id.cmbVehicleBrand);

        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
                R.array.vehicleBrand, android.R.layout.simple_spinner_item);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Brand.setAdapter(adapter2);
    }

    public void imgeAddClass(){
        // Add img 1
        resultLuncher1 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        try {
                            Uri imageUri = result.getData().getData();
                            addImage1.setImageURI(imageUri);
                        } catch (Exception e) {
                            Toast.makeText(createnewadd.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(createnewadd.this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        addImage1.setOnClickListener(v -> displayImg1());

        // Add img 2
        resultLuncher2 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        try {
                            Uri imageUri = result.getData().getData();
                            addImage2.setImageURI(imageUri);
                        } catch (Exception e) {
                            Toast.makeText(createnewadd.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(createnewadd.this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        addImage2.setOnClickListener(v -> displayImg2());

        //Add img 3
        resultLuncher3 = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
                        try {
                            Uri imageUri = result.getData().getData();
                            addImage3.setImageURI(imageUri);
                        } catch (Exception e) {
                            Toast.makeText(createnewadd.this, "Failed to load image", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(createnewadd.this, "No image selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        addImage3.setOnClickListener(v -> displayImg3());
    }

    //Add Images1
    private void displayImg1(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        resultLuncher1.launch(intent);
    }
    //Add Images2
    private void displayImg2(){
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setType("image/*");
        resultLuncher2.launch(intent1);
    }
    //Add Images3
    private void displayImg3(){
        Intent intent1 = new Intent(Intent.ACTION_PICK);
        intent1.setType("image/*");
        resultLuncher3.launch(intent1);
    }


}
