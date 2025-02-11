package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

public class home extends AppCompatActivity {

    private RecyclerView recyclerView;
    public AdsAdapter adsAdapter;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        navigation();

        recyclerView = findViewById(R.id.allAdsRecycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adsAdapter = new AdsAdapter();
        recyclerView.setAdapter(adsAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("ads");

        fetchAdsFromFirebase();

    }

    private void fetchAdsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<AdsInfo> adsInfos = new ArrayList<>();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String category = dataSnapshot.child("category").exists() ? dataSnapshot.child("category").getValue(String.class) : "Unknown";
                    String brand = dataSnapshot.child("brand").getValue(String.class);
                    String model = dataSnapshot.child("model").getValue(String.class);
                    Long milageLong = dataSnapshot.child("milage").getValue(Long.class);
                    Long capacityLong = dataSnapshot.child("capacity").getValue(Long.class);
                    String description = dataSnapshot.child("description").getValue(String.class);
                    Long priceLong = dataSnapshot.child("price").getValue(Long.class);
                    String area = dataSnapshot.child("location").getValue(String.class);

                    int milage = milageLong != null ? milageLong.intValue() : 0;
                    int capacity = capacityLong != null ? capacityLong.intValue() : 0;
                    int price = priceLong != null ? priceLong.intValue() : 0;

                    //first Image URL
                    String firstImageUrl = "";
                    if(dataSnapshot.child("images").exists()){
                        for (DataSnapshot imageSnapshot : dataSnapshot.child("images").getChildren()){
                            firstImageUrl = imageSnapshot.getValue(String.class);
                        }
                    }

                    adsInfos.add(new AdsInfo(category, brand, model, milage, capacity, description, price, area, firstImageUrl));
                }
                adsAdapter.setAdsInfoList(adsInfos);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Log or handle the error
            }
        });
    }


    private void navigation() {
        // buttonCategory
        ImageView imgCategory = findViewById(R.id.btnCategory);
        imgCategory.setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), category.class);
            startActivity(categoryIntent);
        });

        // Add Post
        ImageView imgAddPost = findViewById(R.id.imgAddPost);
        imgAddPost.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = currentUser != null ?
                    new Intent(getApplicationContext(), createnewadd.class) :
                    new Intent(getApplicationContext(), activity_please_login_screen.class);
            startActivity(intent);
        });

        // buttonSearch
        ImageView imgSearchButton = findViewById(R.id.imgSearch);
        imgSearchButton.setOnClickListener(v -> {
            Intent searchButtonIntent = new Intent(getApplicationContext(), search.class);
            startActivity(searchButtonIntent);
        });

        // buttonChat
        ImageView imgChatbutton = findViewById(R.id.imgChats);
        imgChatbutton.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = currentUser != null ?
                    new Intent(getApplicationContext(), chatsNew.class):
                    new Intent(getApplicationContext(), activity_please_login_screen.class);
            startActivity(intent);
        });

        // button profile
        ImageView imgProfileButton = findViewById(R.id.imgProfile);
        imgProfileButton.setOnClickListener(v -> {
            Intent profileButtonIntent = new Intent(getApplicationContext(), profile.class);
            startActivity(profileButtonIntent);
        });
    }
}
