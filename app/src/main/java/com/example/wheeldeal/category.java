package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_category);

        ImageView buttonAllAds = findViewById(R.id.btnAllAds);
        buttonAllAds.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), home.class));
        });

        ImageView btnBack = findViewById(R.id.btnBackImg);
        btnBack.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), home.class));
        });

        TextView txtBack = findViewById(R.id.btntxtBackImg);
        txtBack.setOnClickListener(v -> {
            startActivity(new Intent(getApplicationContext(), home.class));
        });

        ImageView btnMotorBike = findViewById(R.id.btnMotorBike);
        btnMotorBike.setOnClickListener(v -> onCategoryButtonClick(v));

        ImageView btnThreeWheelers = findViewById(R.id.btnthreeWheelers);
        btnThreeWheelers.setOnClickListener(v -> onCategoryButtonClick(v));

        ImageView btnCars = findViewById(R.id.btncars);
        btnCars.setOnClickListener(v -> onCategoryButtonClick(v));

        ImageView btnVans = findViewById(R.id.btnvans);
        btnVans.setOnClickListener(v -> onCategoryButtonClick(v));

        ImageView btnBicycle = findViewById(R.id.btnbicycle);
        btnBicycle.setOnClickListener(v -> onCategoryButtonClick(v));

        ImageView btnBuses = findViewById(R.id.btnbuses);
        btnBuses.setOnClickListener(v -> onCategoryButtonClick(v));

        ImageView btnLorries = findViewById(R.id.btnlorries);
        btnLorries.setOnClickListener(v -> onCategoryButtonClick(v));

        ImageView btnTractors = findViewById(R.id.btntractors);
        btnTractors.setOnClickListener(v -> onCategoryButtonClick(v));

    }

    public void onCategoryButtonClick(View view) {
        String category = "";

        if (view.getId() == R.id.btnMotorBike) {
            category = "Motor Bike";
        } else if (view.getId() == R.id.btnthreeWheelers) {
            category = "Therewheelers";
        } else if (view.getId() == R.id.btncars) {
            category = "Cars";
        } else if (view.getId() == R.id.btnvans) {
            category = "Vans";
        } else if (view.getId() == R.id.btnbicycle) {
            category = "Bicycle";
        } else if (view.getId() == R.id.btnbuses) {
            category = "Bus";
        } else if (view.getId() == R.id.btnlorries) {
            category = "Lorries";
        } else if (view.getId() == R.id.btntractors) {
            category = "Tractors";
        }

        // Start CategoryAds activity with the selected category
        Intent intent = new Intent(category.this, CategoryAds.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }
}
