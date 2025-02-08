package com.example.wheeldeal;

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

public class Edit_number extends AppCompatActivity {

    private EditText editCurrentNumber, editNewNumber;
    private Button btnConfirm, btnCancel;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_number);

        // Initialize Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Initialize UI elements
//        editCurrentNumber = findViewById(R.id.textView62);
//        editNewNumber = findViewById(R.id.textView63);
        btnConfirm = findViewById(R.id.btnupdte2);
        btnCancel = findViewById(R.id.btncancl1);

        // Load current phone number
        loadCurrentPhoneNumber();

        // Set click listeners
        btnConfirm.setOnClickListener(v -> updatePhoneNumber());
        btnCancel.setOnClickListener(v -> finish());
    }

    private void loadCurrentPhoneNumber() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            db.collection("users").document(user.getUid()).get()
                    .addOnSuccessListener(documentSnapshot -> {
                        if (documentSnapshot.exists()) {
                            editCurrentNumber.setText(documentSnapshot.getString("phoneNumber"));
                        }
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(Edit_number.this, "Failed to load phone number", Toast.LENGTH_SHORT).show());
        }
    }

    private void updatePhoneNumber() {
        String newNumber = editNewNumber.getText().toString().trim();

        if (newNumber.isEmpty()) {
            Toast.makeText(this, "Please enter a new phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            db.collection("users").document(user.getUid())
                    .update("phoneNumber", newNumber)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(Edit_number.this, "Phone number updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(Edit_number.this, "Failed to update phone number", Toast.LENGTH_SHORT).show());
        }
    }
}
