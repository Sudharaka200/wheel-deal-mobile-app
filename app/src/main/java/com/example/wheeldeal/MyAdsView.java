package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyAdsView extends AppCompatActivity {

    private MyAdsAdapter adsAdapter;
    private DatabaseReference databaseReference;
    private List<AdsInfo> adsInfoList;
    private RecyclerView recyclerView;

    private FirebaseAuth auth;
    private TextView emailCheck;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_ads_view);

        // Initialize Firebase Auth
        auth = FirebaseAuth.getInstance();
        emailCheck = findViewById(R.id.LoginCheckEmailMyAds);
        user = auth.getCurrentUser();

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.myAdsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the list and adapter
        adsInfoList = new ArrayList<>();
        adsAdapter = new MyAdsAdapter(adsInfoList);
        recyclerView.setAdapter(adsAdapter);

        // Set the item click listener for the adapter
        adsAdapter.setOnItemClickListener(adsInfo -> {
            Intent adViewIntent = new Intent(MyAdsView.this, addView.class);
            adViewIntent.putExtra("category", adsInfo.getaCategory());
            adViewIntent.putExtra("brand", adsInfo.getaBrand());
            adViewIntent.putExtra("milage", adsInfo.getaMilage());
            adViewIntent.putExtra("capacity", adsInfo.getaCapacity());
            adViewIntent.putExtra("description", adsInfo.getaDescription());
            adViewIntent.putExtra("price", adsInfo.getaPrice());
            adViewIntent.putExtra("area", adsInfo.getaLocation());
            startActivity(adViewIntent);
        });

        // Check if the user is logged in
        if (user == null) {
            // Redirect to login activity if the user is not logged in
            // Example: startActivity(new Intent(MyAdsView.this, LoginActivity.class));
            finish();
        } else {
            // Set the user's email in the TextView
            emailCheck.setText(user.getEmail());

            // Fetch ads from Firebase
            fetchAdsFromFirebase();
        }
    }

    private void fetchAdsFromFirebase() {
        if (user == null) {
            return;
        }

        String userEmail = user.getEmail();
        databaseReference = FirebaseDatabase.getInstance().getReference("ads"); // Ensure this points to the correct node

        databaseReference.orderByChild("email").equalTo(userEmail).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adsInfoList.clear();  // Clear list before adding new data
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String category = dataSnapshot.child("category").exists() ? dataSnapshot.child("category").getValue(String.class) : "Unknown";
                    String brand = dataSnapshot.child("brand").getValue(String.class);
                    String model = dataSnapshot.child("model").getValue(String.class);
                    Long milageLong = dataSnapshot.child("milage").getValue(Long.class);
                    Long capacityLong = dataSnapshot.child("capacity").getValue(Long.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    Long priceLong = dataSnapshot.child("price").getValue(Long.class);
                    String area = dataSnapshot.child("location").getValue(String.class);
                    String email = dataSnapshot.child("email").getValue(String.class);

                    int milage = milageLong != null ? milageLong.intValue() : 0;
                    int capacity = capacityLong != null ? capacityLong.intValue() : 0;
                    int price = priceLong != null ? priceLong.intValue() : 0;

                    String firstImageUrl = "";
                    if (dataSnapshot.child("images").exists()) {
                        for (DataSnapshot imageSnapshot : dataSnapshot.child("images").getChildren()) {
                            firstImageUrl = imageSnapshot.getValue(String.class);
                            break;
                        }
                    }

                    adsInfoList.add(new AdsInfo(category, brand, model, milage, capacity, description, price, area, firstImageUrl, email));
                }

                adsAdapter.setAdsInfoList(adsInfoList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Error fetching ads: " + error.getMessage());
            }
        });
    }


}

