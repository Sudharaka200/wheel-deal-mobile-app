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

        buttonAllAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), home.class);
                startActivity(i);
            }
        });

        ImageView btnBack = findViewById(R.id.btnBackImg);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntentImg = new Intent(getApplicationContext(), home.class);
                startActivity(homeIntentImg);
            }
        });

        TextView txtBack = findViewById(R.id.btntxtBackImg);

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntentBack = new Intent(getApplicationContext(), home.class);
                startActivity(homeIntentBack);
            }
        });

        ImageView buttoMmortorBike = findViewById(R.id.btnMortorBike);

        buttoMmortorBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CategoryAds.class);
                startActivity(i);
            }
        });

    }
}