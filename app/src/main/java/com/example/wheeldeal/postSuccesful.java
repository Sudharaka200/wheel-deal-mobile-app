package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class postSuccesful extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_post_succesful);

        Button btnGoMyAds = findViewById(R.id.btnGoMyAds);
        btnGoMyAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goMyAdsIntent = new Intent(getApplicationContext(), Myads.class);
                startActivity(goMyAdsIntent);
            }
        });
    }
}