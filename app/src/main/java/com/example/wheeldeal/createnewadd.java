package com.example.wheeldeal;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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

    private Spinner categoryEdt, brandEdt;
    private EditText milageEdt, capacityEdt, descriptionEdt, priceEdt, locationEdt;
    private Button btnPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createnewadd);

        // Initialize UI elements
        categoryEdt = findViewById(R.id.cmbVehicleCategory);
        brandEdt = findViewById(R.id.cmbVehicleBrand);
        milageEdt = findViewById(R.id.txtMilage);
        capacityEdt = findViewById(R.id.txtCapacity);
        descriptionEdt = findViewById(R.id.txtDescription);
        priceEdt = findViewById(R.id.txtPrice);
        locationEdt = findViewById(R.id.txtLocation);
        btnPost = findViewById(R.id.btnPostAd);

        addImage1 = findViewById(R.id.imgAdd1);
        addImage2 = findViewById(R.id.imgAdd2);
        addImage3 = findViewById(R.id.imgAdd3);

        cmbbox();  // Initialize the spinners
        setupImagePickers();  // Setup image selection

        btnPost.setOnClickListener(v -> {
            insertTxtDB();
        });
    }

    private void uploadMultipleImages() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference("ads_images");

        Uri[] imageUris = {imageUri1, imageUri2, imageUri3};

        for (int i = 0; i < imageUris.length; i++) {
            if (imageUris[i] == null) continue;

            String imageName = System.currentTimeMillis() + "_img" + (i + 1) + ".jpg";
            StorageReference imageRef = storageReference.child(imageName);

            imageRef.putFile(imageUris[i])
                    .addOnSuccessListener(taskSnapshot -> imageRef.getDownloadUrl()
                            .addOnSuccessListener(uri -> {
                                String downloadUrl = uri.toString();
                                saveImageUrlToDatabase(downloadUrl);
                            }))
                    .addOnFailureListener(e -> {
                        Toast.makeText(createnewadd.this, "Failed to upload image: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        }
    }

    private void saveImageUrlToDatabase(String imageUrl) {
        DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference("ads_images");
        String key = dbRef.push().getKey();
        dbRef.child(key).setValue(imageUrl)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(createnewadd.this, "Image URL saved to database!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(createnewadd.this, "Failed to save image URL.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void insertTxtDB() {
        String categoryD = categoryEdt.getSelectedItem().toString().trim();
        String brandD = brandEdt.getSelectedItem().toString().trim();
        int mileageD = !milageEdt.getText().toString().trim().isEmpty() ? Integer.parseInt(milageEdt.getText().toString().trim()) : 0;
        int capacityD = Integer.parseInt(capacityEdt.getText().toString().trim());
        String descriptionD = descriptionEdt.getText().toString();
        int priceD = Integer.parseInt(priceEdt.getText().toString().trim());
        String locationD = locationEdt.getText().toString();

        if (categoryD.isEmpty() || brandD.isEmpty() || mileageD == 0 || capacityD == 0 || descriptionD.isEmpty() || priceD == 0 || locationD.isEmpty()) {
            Toast.makeText(this, "All fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

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

        dbRef.child(key).setValue(dbHashMap).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(createnewadd.this, "Ad posted successfully", Toast.LENGTH_SHORT).show();
                uploadMultipleImages();  // Upload images after posting the ad
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
