package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
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

public class search extends AppCompatActivity {

    private RecyclerView recyclerView;
    public AdsAdapter adsAdapter;
    private DatabaseReference databaseReference;

    private List<AdsInfo> adsInfoList;

    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

        navigation();

        searchView = findViewById(R.id.searchView1);
        searchView.clearFocus();

        recyclerView = findViewById(R.id.allAdsRecycleView1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adsAdapter = new AdsAdapter(adsInfoList);
        recyclerView.setAdapter(adsAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("ads");

        fetchAdsFromFirebase();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });

        adsAdapter.setOnItemClickListener(adsInfo -> {
            Intent intent = new Intent(search.this, addView.class);
            intent.putExtra("category", adsInfo.getaCategory());
            intent.putExtra("brand", adsInfo.getaBrand());
            intent.putExtra("milage", adsInfo.getaMilage());
            intent.putExtra("capacity", adsInfo.getaCapacity());
            intent.putExtra("description", adsInfo.getaDescription());
            intent.putExtra("price", adsInfo.getaPrice());
            intent.putExtra("area", adsInfo.getaLocation());
            startActivity(intent);
        });
    }


    private void fetchAdsFromFirebase() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                adsInfoList = new ArrayList<>();  // Initialize the list
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
                // Log or handle the error
            }
        });



        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }

    public void searchList(String text) {
        ArrayList<AdsInfo> searchList = new ArrayList<>();
        for (AdsInfo adsInfo : adsInfoList) {
            if ((adsInfo.getaModel() != null && adsInfo.getaModel().toLowerCase().contains(text.toLowerCase())) ||
                    (adsInfo.getaBrand() != null && adsInfo.getaBrand().toLowerCase().contains(text.toLowerCase())) ||
                    (adsInfo.getaCategory() != null && adsInfo.getaCategory().toLowerCase().contains(text.toLowerCase())) ||
                    (adsInfo.getaLocation() != null && adsInfo.getaLocation().toLowerCase().contains(text.toLowerCase()))) {
                searchList.add(adsInfo);
            }
        }
        adsAdapter.searchAdsList(searchList);
    }

    private void navigation() {

        // Add Post
        ImageView imgAddPost = findViewById(R.id.imgAddPost);
        imgAddPost.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = currentUser != null ?
                    new Intent(getApplicationContext(), createnewadd.class) :
                    new Intent(getApplicationContext(), activity_please_login_screen.class);
            startActivity(intent);
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

        //backButton
        ImageView backbuttonSeach = findViewById(R.id.btnBackSearch);

        backbuttonSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backButtonSearch = new Intent(getApplicationContext(),home.class);
                startActivity(backButtonSearch);
            }
        });
        ImageView imgHomeButton = findViewById(R.id.imgHome);

        imgHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeButtonIntent = new Intent(getApplicationContext(), home.class);
                startActivity(homeButtonIntent);
            }
        });

    }
}