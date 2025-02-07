package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

public class Edit_name extends AppCompatActivity {

    private EditText editFirstName, editLastName;
    private Button btnVerify, btnCancel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_name);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
        editFirstName = findViewById(R.id.textView60);
        editLastName = findViewById(R.id.textView59);
        btnVerify = findViewById(R.id.btnverify1);
        btnCancel = findViewById(R.id.btncancel24);

        // Load current user's data
        loadUserData();

        // Set click listeners
        btnVerify.setOnClickListener(v -> updateUserData());
        btnCancel.setOnClickListener(v -> finish()); // Go back to profile
    }

    private void loadUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            db.collection("users").document(user.getUid()).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            editFirstName.setText(documentSnapshot.getString("firstName"));
                            editLastName.setText(documentSnapshot.getString("lastName"));
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(Edit_name.this, "Failed to load user data", Toast.LENGTH_SHORT).show());
        }
    }

    private void updateUserData() {
        String newFirstName = editFirstName.getText().toString().trim();
        String newLastName = editLastName.getText().toString().trim();

        if (newFirstName.isEmpty() || newLastName.isEmpty()) {
            Toast.makeText(this, "Both fields must be filled", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            db.collection("users").document(user.getUid())
                    .update("firstName", newFirstName, "lastName", newLastName)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Edit_name.this, "Name updated successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Go back to profile
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(Edit_name.this, "Failed to update name", Toast.LENGTH_SHORT).show());
        }
    }
}
