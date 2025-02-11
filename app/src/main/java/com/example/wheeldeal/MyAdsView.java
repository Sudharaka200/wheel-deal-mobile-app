package com.example.wheeldeal;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdsView extends AppCompatActivity {

    public AdsAdapter adsAdapter;
    DatabaseReference databaseReference;
    private List<AdsInfo> adsInfoList;

    FirebaseAuth auth;
    TextView emailCheck;
    FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_ads_view);

        //user check
        auth = FirebaseAuth.getInstance();
        emailCheck = findViewById(R.id.LoginCheckEmailMyAds);
        user = auth.getCurrentUser();
        if (user == null) {

        } else {
            emailCheck.setText(user.getEmail());
        }


    }






}