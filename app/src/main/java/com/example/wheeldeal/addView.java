package com.example.wheeldeal;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class addView extends AppCompatActivity {

    // Declare all the views
    private TextView txtCategory, txtBrand, txtMilage, txtCapacity, txtDescription, txtPrice, txtArea;
    private TextView txtBack, textView15, textView16, textView17, textView18, textView19, textView28;

    TextView txtAddLocationD;
    TextView txtMyLocationD;
    Button btnLocationD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_view);

        // Initialize all the views
        //btnBackButton = findViewById(R.id.btnBack);
        txtBack = findViewById(R.id.txtBack);
        textView15 = findViewById(R.id.textView15);
        textView16 = findViewById(R.id.textView16);
        textView17 = findViewById(R.id.textView17);
        textView18 = findViewById(R.id.textView18);
        textView19 = findViewById(R.id.textView19);
        textView28 = findViewById(R.id.textView28);
//        btnLocation = findViewById(R.id.button);
//        btnCall = findViewById(R.id.btncancel22);
//        btnChat = findViewById(R.id.button4);

        Intent intent = getIntent();
        if (intent != null) {
            String category = intent.getStringExtra("category");
            String brand = intent.getStringExtra("brand");
            int milage = intent.getIntExtra("milage", 0);
            int capacity = intent.getIntExtra("capacity", 0);
            String description = intent.getStringExtra("description");
            int price = intent.getIntExtra("price", 0);
            String area = intent.getStringExtra("area");

            // Populate the views with the ad details
            textView15.setText(brand); // Brand
            textView16.setText(area); // Location
            textView17.setText("Rs"); // Currency symbol
            textView18.setText(String.valueOf(price)); // Price
            textView19.setText("Description"); // Description label
            textView28.setText(description); // Description text

            TextView txtGetLocation = findViewById(R.id.txtAddLocationView);
            txtGetLocation.setText(area);
        }
        navigation();
        googleMapLocation();


    }


    public void googleMapLocation(){

        txtAddLocationD =  findViewById(R.id.txtAddLocationView);
        txtMyLocationD = findViewById(R.id.txtGetMyLocation);
        btnLocationD =  findViewById(R.id.btnLocation);

        btnLocationD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txtAdLocation = txtAddLocationD.getText().toString().trim();
                String txtMyLocation =  txtMyLocationD.getText().toString().trim();

                if (txtAdLocation.isEmpty() || txtMyLocation.isEmpty()) {
                    Toast.makeText(v.getContext(), "Your Location Not Found", Toast.LENGTH_SHORT).show();
                } else {
                    getDirections(txtMyLocation, txtAdLocation); // Corrected parameter order
                }
            }
        });
    }
    private void getDirections(String from, String to) {
        try {
            Uri uri = Uri.parse("http://www.google.com/maps/dir/" + Uri.encode(from) + "/" + Uri.encode(to));
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException exception) {
            Uri uri = Uri.parse("http://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }





//    public void locationName(){
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = database.getReference("Ads");
//
//        TextView txtGetLocation = findViewById(R.id.txtGetLocationView);
//        if (area =! null){
//            String userID = area.getUid;
//
//            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("Ads");
//
//            databaseReference1.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if (snapshot.exists()){
//                        String name = snapshot.child("location").getValue(String.class);
//                        txtGetLocation.setText(location);
//                    }else {
//                        txtGetLocation.setText("Location not Found");
//                    }
//
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//
//
//    }

    public void navigation(){
        Button buttonCall =  findViewById(R.id.btnCall);
        buttonCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button buttonChat =  findViewById(R.id.btnChat);
        buttonChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(getApplicationContext(), chatsNew.class);
                startActivity(chatIntent);
            }
        });

        Button buttonLocation =  findViewById(R.id.btnLocation);
        buttonLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent locationIntent = new Intent(getApplicationContext(), googleMap.class);
//                startActivity(locationIntent);

            }
        });





        ImageView btnBackbutton = findViewById(R.id.btnBack);

        btnBackbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent BackIntent = new Intent(getApplicationContext(), home.class);
                startActivity(BackIntent);
            }
        });

        TextView txtBackButton = findViewById(R.id.txtBack);

        txtBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent Backtxtintent = new Intent(getApplicationContext(), home.class);
                startActivity(Backtxtintent);
            }
        });
    }


}