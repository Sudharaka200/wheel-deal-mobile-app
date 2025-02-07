package com.example.wheeldeal;

import static java.security.AccessController.getContext;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;


public class createnewadd extends AppCompatActivity {

    ImageView addImage1;
    ImageView addImage2;
    ImageView addImage3;
    ActivityResultLauncher<Intent> resultLuncher1;
    ActivityResultLauncher<Intent> resultLuncher2;
    ActivityResultLauncher<Intent> resultLuncher3;

    Spinner vCategory;
    Spinner vBrand;
    ImageView vImg1;
    ImageView vImg2;
    ImageView vImg3;
    TextView vMilage;
    TextView vCapacity;
    TextView vDescription;
    TextView vPrice;
    TextView vLocation;
    Button vBtnSave;
    SQLiteHelper sqLiteHelper;


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
//        intiAdd();

        vCategory = (Spinner) findViewById(R.id.cmbVehicleCategory);
        vBrand = (Spinner) findViewById(R.id.cmbVehicleBrand);
        vImg1 = (ImageView) findViewById(R.id.imgAdd1);
        vImg2 = (ImageView) findViewById(R.id.imgAdd2);
        vImg3 = (ImageView) findViewById(R.id.imgAdd3);
        vMilage = (TextView) findViewById(R.id.txtMilage);
        vCapacity = (TextView) findViewById(R.id.txtCapacity);
        vDescription = (TextView) findViewById(R.id.Description);
        vPrice = (TextView) findViewById(R.id.txtPrice);
        vLocation = (TextView) findViewById(R.id.txtLocation);
        vBtnSave = findViewById(R.id.btnConfirm);


        sqLiteHelper = new SQLiteHelper(createnewadd.this);

        vBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String category = vCategory.getSelectedItem().toString();
                String brand = vBrand.getSelectedItem().toString();
                int milage = Integer.parseInt(vMilage.getText().toString());
                String capacity = vCapacity.getText().toString();
                String description = vDescription.getText().toString();
                String price = vPrice.getText().toString();
                String location = vLocation.getText().toString();

                // Extract images from ImageView
                Bitmap bitmap1 = ((BitmapDrawable) addImage1.getDrawable()).getBitmap();
                ByteArrayOutputStream outputStream1 = new ByteArrayOutputStream();
                bitmap1.compress(Bitmap.CompressFormat.PNG, 100, outputStream1);
                byte[] img1Bytes = outputStream1.toByteArray();

                Bitmap bitmap2 = ((BitmapDrawable) addImage2.getDrawable()).getBitmap();
                ByteArrayOutputStream outputStream2 = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.PNG, 100, outputStream2);
                byte[] img2Bytes = outputStream2.toByteArray();

                Bitmap bitmap3 = ((BitmapDrawable) addImage3.getDrawable()).getBitmap();
                ByteArrayOutputStream outputStream3 = new ByteArrayOutputStream();
                bitmap3.compress(Bitmap.CompressFormat.PNG, 100, outputStream3);
                byte[] img3Bytes = outputStream3.toByteArray();


                if (addImage1.getDrawable() == null || addImage2.getDrawable() == null || addImage3.getDrawable() == null) {
                    Toast.makeText(createnewadd.this, "Please select all images", Toast.LENGTH_SHORT).show();
                    return;
                }


                sqLiteHelper.createNewAdd(category, brand, img1Bytes, img2Bytes, img3Bytes, milage, capacity, description, price, location);

                Toast.makeText(createnewadd.this, "Add posted", Toast.LENGTH_SHORT).show();

            }
        });


    }
//    private void clearFields() {
//        vCategory.setSelection(0);
//        vBrand.setSelection(0);
//        vMilage.setText("");
//        vCapacity.setText("");
//        vDescription.setText("");
//        vPrice.setText("");
//        vLocation.setText("");
//        addImage1.setImageResource(R.drawable.image__1_);
//        addImage2.setImageResource(R.drawable.image__1_);
//        addImage3.setImageResource(R.drawable.image__1_);
//    }


//    private void intiAdd(){
//        vCategory = (Spinner) findViewById(R.id.cmbVehicleCategory);
//        vBrand = (Spinner) findViewById(R.id.cmbVehicleBrand);
//        vImg1 = (ImageView) findViewById(R.id.imgAdd1);
//        vImg2 = (ImageView) findViewById(R.id.imgAdd2);
//        vImg3 = (ImageView) findViewById(R.id.imgAdd3);
//        vMilage = (TextView) findViewById(R.id.txtMilage);
//        vCapacity = (TextView) findViewById(R.id.txtCapacity);
//        vDescription = (TextView) findViewById(R.id.Description);
//        vPrice = (TextView) findViewById(R.id.txtPrice);
//        vLocation = (TextView) findViewById(R.id.txtLocation);
//
//        sqLiteHelper = new SQLiteHelper(createnewadd.this);
//
//        vBtnSave.setOnClickListener();
//    }


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
