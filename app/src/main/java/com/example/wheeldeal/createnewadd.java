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
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;
import java.sql.Blob;
import java.util.HashMap;


public class createnewadd extends AppCompatActivity {

//    ImageView addImage1;
//    ImageView addImage2;
//    ImageView addImage3;
//    ActivityResultLauncher<Intent> resultLuncher1;
//    ActivityResultLauncher<Intent> resultLuncher2;
//    ActivityResultLauncher<Intent> resultLuncher3;

    //DB insert
    private Spinner categoryEdt, brandEdt;
    private EditText  milageEdt, capacityEdt, descriptionEdt,priceEdt, locationEdt;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_createnewadd);

        //Combobox
        cmbbox();

        //Images Add for create new add
//        addImage1 = findViewById(R.id.imgAdd1);
//        addImage2 = findViewById(R.id.imgAdd2);
//        addImage3 = findViewById(R.id.imgAdd3);
//        imgeAddClass();


        //Insert DB
        categoryEdt = findViewById(R.id.cmbVehicleCategory);
        brandEdt = findViewById(R.id.cmbVehicleBrand);
        milageEdt = findViewById(R.id.txtMilage);
        capacityEdt = findViewById(R.id.txtCapacity);
        descriptionEdt = findViewById(R.id.txtDescription);
        priceEdt = findViewById(R.id.txtPrice);
        locationEdt = findViewById(R.id.txtLocation);
        btnPost = findViewById(R.id.btnPostAd);

        try {
            btnPost.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String categoryD = categoryEdt.getSelectedItem().toString().trim();
                    String brandD = brandEdt.getSelectedItem().toString().trim();
                    int mileageD = !milageEdt.getText().toString().trim().isEmpty() ?
                            Integer.parseInt(milageEdt.getText().toString().trim()) : 0;
                    int capacityD = Integer.parseInt(capacityEdt.getText().toString().trim());
                    String descriptionD  = descriptionEdt.getText().toString();
                    int priceD = Integer.parseInt(priceEdt.getText().toString().trim());
                    String locationD = locationEdt.getText().toString();

                    if (categoryD.isEmpty()){
                        Toast.makeText(createnewadd.this,"cannot be Empty",Toast.LENGTH_SHORT).show();
                    }
                    if (brandD.isEmpty()){
                        Toast.makeText(createnewadd.this,"cannot be Empty",Toast.LENGTH_SHORT).show();
                    }
                    if (mileageD == 0){
                        Toast.makeText(createnewadd.this,"cannot be Empty",Toast.LENGTH_SHORT).show();
                    }
                    if (capacityD == 0){
                        Toast.makeText(createnewadd.this,"cannot be Empty",Toast.LENGTH_SHORT).show();
                    }
                    if (descriptionD.isEmpty()){
                        Toast.makeText(createnewadd.this,"cannot be Empty",Toast.LENGTH_SHORT).show();
                    }
                    if (priceD == 0){
                        Toast.makeText(createnewadd.this,"cannot be Empty",Toast.LENGTH_SHORT).show();
                    }
                    if (locationD.isEmpty()){
                        Toast.makeText(createnewadd.this,"cannot be Empty",Toast.LENGTH_SHORT).show();
                    }
                    addQuoteToDB(categoryD, brandD, mileageD, capacityD, descriptionD, priceD, locationD);
                }
            });
        }catch (Exception e){
            Toast.makeText(createnewadd.this,"Error",Toast.LENGTH_SHORT).show();
        }
    }

    private void addQuoteToDB(String categoryD, String brandD, int mileageD, int capacityD, String descriptionD, int priceD, String locationD) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("ads");
        String key = dbRef.push().getKey();

        HashMap<String, Object> dbHashMap = new HashMap<>();
        dbHashMap.put("category", categoryD);
        dbHashMap.put("brand", brandD);
        dbHashMap.put("mileage", mileageD);
        dbHashMap.put("capacity", capacityD);
        dbHashMap.put("description", descriptionD);
        dbHashMap.put("price", priceD);
        dbHashMap.put("location", locationD);

        dbRef.child(key).setValue(dbHashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(createnewadd.this, "Ad post successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(createnewadd.this, "Ad post successfully Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
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

//    public void imgeAddClass(){
//        // Add img 1
//        resultLuncher1 = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
//                        try {
//                            Uri imageUri = result.getData().getData();
//                            addImage1.setImageURI(imageUri);
//                        } catch (Exception e) {
//                            Toast.makeText(createnewadd.this, "Failed to load image", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(createnewadd.this, "No image selected", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//        addImage1.setOnClickListener(v -> displayImg1());
//
//        // Add img 2
//        resultLuncher2 = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
//                        try {
//                            Uri imageUri = result.getData().getData();
//                            addImage2.setImageURI(imageUri);
//                        } catch (Exception e) {
//                            Toast.makeText(createnewadd.this, "Failed to load image", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(createnewadd.this, "No image selected", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//        addImage2.setOnClickListener(v -> displayImg2());
//
//        //Add img 3
//        resultLuncher3 = registerForActivityResult(
//                new ActivityResultContracts.StartActivityForResult(),
//                result -> {
//                    if (result.getResultCode() == Activity.RESULT_OK && result.getData() != null) {
//                        try {
//                            Uri imageUri = result.getData().getData();
//                            addImage3.setImageURI(imageUri);
//                        } catch (Exception e) {
//                            Toast.makeText(createnewadd.this, "Failed to load image", Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//                        Toast.makeText(createnewadd.this, "No image selected", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        );
//        addImage3.setOnClickListener(v -> displayImg3());
//    }
//
//    //Add Images1
//    private void displayImg1(){
//        Intent intent = new Intent(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        resultLuncher1.launch(intent);
//    }
//    //Add Images2
//    private void displayImg2(){
//        Intent intent1 = new Intent(Intent.ACTION_PICK);
//        intent1.setType("image/*");
//        resultLuncher2.launch(intent1);
//    }
//    //Add Images3
//    private void displayImg3(){
//        Intent intent1 = new Intent(Intent.ACTION_PICK);
//        intent1.setType("image/*");
//        resultLuncher3.launch(intent1);
//    }


}
