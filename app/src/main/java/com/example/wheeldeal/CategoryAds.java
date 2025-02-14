package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import androidx.appcompat.widget.SearchView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

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

public class CategoryAds extends AppCompatActivity {
    private SeekBar minPriceSeekBar, maxPriceSeekBar;
    private EditText minPriceLabel, maxPriceLabel;
    private RecyclerView recyclerView;
    private AdsAdapter adsAdapter;
    private DatabaseReference databaseReference;
    private List<AdsInfo> adsInfoList = new ArrayList<>();
    private SearchView searchView;
    private int minPrice = 10000, maxPrice = 100000000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.category_ads);

        minPriceSeekBar = findViewById(R.id.minPriceSeekBar);
        maxPriceSeekBar = findViewById(R.id.maxPriceSeekBar);
        minPriceLabel = findViewById(R.id.minPriceLabel);
        maxPriceLabel = findViewById(R.id.maxPriceLabel);
        searchView = findViewById(R.id.searchViewCtg);
        recyclerView = findViewById(R.id.recyclerViewCtg);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference = FirebaseDatabase.getInstance().getReference("ads");
        adsAdapter = new AdsAdapter();
        recyclerView.setAdapter(adsAdapter);

        fetchAdsFromFirebase();
        setupSearchView();
        setupSeekBars();
        setupPriceInputs();
        navigation();

        adsAdapter.setOnItemClickListener(adsInfo -> {
            Intent intent = new Intent(CategoryAds.this, addView.class);
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

    private void setupSeekBars() {
        minPriceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress >= maxPriceSeekBar.getProgress()) {
                    seekBar.setProgress(maxPriceSeekBar.getProgress() - 1000);
                }
                minPrice = progress;
                minPriceLabel.setText(String.valueOf(progress));
                filterAds();
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        maxPriceSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (progress <= minPriceSeekBar.getProgress()) {
                    seekBar.setProgress(minPriceSeekBar.getProgress() + 1000);
                }
                maxPrice = progress;
                maxPriceLabel.setText(String.valueOf(progress));
                filterAds();
            }

            @Override public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void setupPriceInputs() {
        minPriceLabel.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int value = Integer.parseInt(s.toString());
                    if (value >= 10000 && value <= maxPriceSeekBar.getMax()) {
                        minPriceSeekBar.setProgress(value);
                        minPrice = value;
                        filterAds();
                    }
                } catch (NumberFormatException e) {
                    Log.e("CategoryAds", "Invalid min price input");
                }
            }
        });

        maxPriceLabel.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                try {
                    int value = Integer.parseInt(s.toString());
                    if (value >= minPriceSeekBar.getProgress() && value <= 100000000) {
                        maxPriceSeekBar.setProgress(value);
                        maxPrice = value;
                        filterAds();
                    }
                } catch (NumberFormatException e) {
                    Log.e("CategoryAds", "Invalid max price input");
                }
            }
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

                    adsInfoList.add(new AdsInfo(category, brand, model, milage, capacity, description, price, area, firstImageUrl));
                }
                adsAdapter.setAdsInfoList(adsInfoList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private void filterAds() {
        List<AdsInfo> filteredAds = new ArrayList<>();
        for (AdsInfo ad : adsInfoList) {
            if (ad.getaPrice() >= minPrice && ad.getaPrice() <= maxPrice) {
                filteredAds.add(ad);
            }
        }
        adsAdapter.setAdsInfoList(filteredAds);
    }

    private void setupSearchView() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
    }

    private void searchList(String text) {
        List<AdsInfo> searchList = new ArrayList<>();
        for (AdsInfo adsInfo : adsInfoList) {
            String brand = adsInfo.getaBrand() != null ? adsInfo.getaBrand().toLowerCase() : "";
            String model = adsInfo.getaModel() != null ? adsInfo.getaModel().toLowerCase() : "";
            String query = text != null ? text.toLowerCase() : "";

            if (brand.contains(query) || model.contains(query)) {
                searchList.add(adsInfo);
            }
        }
        adsAdapter.setAdsInfoList(searchList);
    }
    private void navigation() {
        // buttonCategories
        TextView imgCategories = findViewById(R.id.btnBackToCtg);
        imgCategories.setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), category.class);
            startActivity(categoryIntent);
        });

        ImageView imgBack = findViewById(R.id.btnBackImg3);
        imgBack.setOnClickListener(v -> {
            Intent categoryIntent = new Intent(getApplicationContext(), category.class);
            startActivity(categoryIntent);
        });
    }
}
