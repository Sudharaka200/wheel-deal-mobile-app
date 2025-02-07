package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;

public class profile extends AppCompatActivity {

    TextView  txtFullName, txtPhone, txtAddress;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        txtFullName = findViewById(R.id.textView48);
        txtPhone = findViewById(R.id.textProfilePhone);
        txtAddress = findViewById(R.id.textProfileAddress);

        loadUserProfile();


        //buttonHome
        ImageView imgHomeButton = findViewById(R.id.imgHome);

        imgHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeButtonIntent = new Intent(getApplicationContext(), home.class);
                startActivity(homeButtonIntent);
            }
        });

        //buttonSearch
        ImageView imgSearchButton = findViewById(R.id.imgSearch);

        imgSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchButtonIntent = new Intent(getApplicationContext(), search.class);
                startActivity(searchButtonIntent);
            }
        });

        //buttonChat
        ImageView imgChatbutton = findViewById(R.id.imgChats);

        imgChatbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatButtonIntent = new Intent(getApplicationContext(), chatsNew.class);
                startActivity(chatButtonIntent);
            }
        });

        //button profile
//        ImageView imgProfileButton = findViewById(R.id.imgProfile);
//
//        imgProfileButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent profileButtonIntent = new Intent(getApplicationContext(), profile.class);
//                startActivity(profileButtonIntent);
//            }
//        });
    }
    public void loadUserProfile(){
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            db.collection("users").document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful() && task.getResult() != null) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                String firstName = document.getString("firstName");
                                String lastName = document.getString("lastName");
                                String phone = document.getString("phoneNumber");
                                String address = document.getString("address");

                                // Display full name
                                txtFullName.setText(firstName + " " + lastName);
                                txtPhone.setText(phone);
                                txtAddress.setText(address);
                            }
                        } else {
                            Toast.makeText(profile.this, "Failed to load profile", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
}