package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profile extends AppCompatActivity {

    TextView  txtFullName, txtPhone, txtAddress;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    View imgLogout, btnDeleteAccount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.profile);
        navigation();

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();


        txtFullName = findViewById(R.id.textView48);
        txtPhone = findViewById(R.id.textProfilePhone);
        txtAddress = findViewById(R.id.textProfileAddress);
        imgLogout = findViewById(R.id.btnProfileLogout);
        btnDeleteAccount = findViewById(R.id.btnDeleteAccount);

        loadUserProfile();

        imgLogout.setOnClickListener(v -> logoutUser());

        btnDeleteAccount.setOnClickListener(v -> confirmDeleteAccount());

    }

    public void navigation(){
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
        imgChatbutton.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = currentUser != null ?
                    new Intent(getApplicationContext(), chatsNew.class) :
                    new Intent(getApplicationContext(), activity_please_login_screen.class);
            startActivity(intent);
        });

        // Add Post Button
        ImageView imgAddPost = findViewById(R.id.imgAddPost);
        imgAddPost.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = currentUser != null ?
                    new Intent(getApplicationContext(), createnewadd.class) :
                    new Intent(getApplicationContext(), activity_please_login_screen.class);
            startActivity(intent);
        });

//        // Name Update Button
//        ImageView btnNameButton = findViewById(R.id.btnName);
//        btnNameButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Edit_name.class)));
//
//        // Phone Number Update Button
//        ImageView btnPhoneButton = findViewById(R.id.btnPhoneNumber);
//        btnPhoneButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Edit_number.class)));
//
//        // Address Update Button
//        ImageView btnAddressButton = findViewById(R.id.btnAddress);
//        btnAddressButton.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(), Edit_gmail.class)));



        ImageView btnPhone =  findViewById(R.id.btnPhoneNumber);
        btnPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent btnP = new Intent(getApplicationContext(), Edit_number.class);
                startActivity(btnP);
            }
        });

        ImageView btnCreateNewAdd = findViewById(R.id.createAdvertisment);
        btnCreateNewAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent createAddIntent = new Intent(getApplicationContext(), createnewadd.class);
                startActivity(createAddIntent);
            }
        });

        ImageView btnMyAds = findViewById(R.id.btnMyAds);
        btnMyAds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myAdsIntent = new Intent(getApplicationContext(), Myads.class);
                startActivity(myAdsIntent);
            }
        });

        ImageView btnFavList = findViewById(R.id.btnFavouriteList);
        btnFavList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent favListIntent = new Intent(getApplicationContext(), favourites.class);
                startActivity(favListIntent);
            }
        });
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

    private void logoutUser() {
        mAuth.signOut();
        Toast.makeText(profile.this, "Logged out successfully", Toast.LENGTH_SHORT).show();

        // Redirect to login page
        Intent intent = new Intent(profile.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void openActivity(Class<?> activityClass) {
        Intent intent = new Intent(profile.this, activityClass);
        startActivity(intent);
    }
    private void confirmDeleteAccount() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Account");
        builder.setMessage("Are you sure you want to delete your account? This action cannot be undone.");

        builder.setPositiveButton("Delete", (dialog, which) -> deleteAccount());
        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteAccount() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String userId = user.getUid();

            // Delete Firestore user data
            db.collection("users").document(userId)
                    .delete()
                    .addOnSuccessListener(aVoid -> {
                        // Delete Firebase Auth account
                        user.delete()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(profile.this, "Account deleted successfully", Toast.LENGTH_LONG).show();
                                        redirectToLogin();
                                    } else {
                                        Toast.makeText(profile.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    })
                    .addOnFailureListener(e -> Toast.makeText(profile.this, "Failed to delete user data", Toast.LENGTH_SHORT).show());
        }
    }
    private void redirectToLogin() {
        Intent intent = new Intent(profile.this, login.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }



}