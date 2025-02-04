package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class createAccount extends AppCompatActivity {

    EditText firstname;
    EditText lastname;
    EditText email;
    EditText phonenumber;
    EditText address;
    EditText password;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_create_account);

        firstname = findViewById(R.id.txtFirstName);
        lastname = findViewById(R.id.txtLastName);
        email = findViewById(R.id.txtEmail);
        phonenumber = findViewById(R.id.txtPhoneNumber);
        address = findViewById(R.id.txtAddress);
        password = findViewById(R.id.txtPassword);
        TextView btnSignUp = findViewById(R.id.btnlogin);
        mAuth = FirebaseAuth.getInstance();

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ufirstname ,ulastname, uemail, uaddress, upassword, uphonenumber;
//                int uphonenumber;

                ufirstname = String.valueOf(firstname.getText());
                ulastname = String.valueOf(lastname.getText());
                uemail = String.valueOf(email.getText());
//                uphonenumber = Integer.parseInt(phonenumber.getText().toString());
                uphonenumber = String.valueOf(phonenumber.getText());
                uaddress = String.valueOf(address.getText());
                upassword = String.valueOf(password.getText());

                if (TextUtils.isEmpty(ufirstname)){
                    Toast.makeText(createAccount.this, "First Name is Empty !" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(ulastname)){
                    Toast.makeText(createAccount.this, "First Name is Empty !" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(uemail)){
                    Toast.makeText(createAccount.this, "First Name is Empty !" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(uphonenumber)){
                    Toast.makeText(createAccount.this, "Phone Number is Empty !" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(uaddress)){
                    Toast.makeText(createAccount.this, "First Name is Empty !" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(upassword)){
                    Toast.makeText(createAccount.this, "First Name is Empty !" , Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(uemail, upassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser user = mAuth.getCurrentUser(); // Get the newly created user

                                    // Create a map to store the additional user data
                                    Map<String, Object> userData = new HashMap<>();
                                    userData.put("firstName", ufirstname);
                                    userData.put("lastName", ulastname);
                                    userData.put("phoneNumber", uphonenumber); // Store as a String!
                                    userData.put("address", uaddress);
                                    // ... add other user data

                                    // Store the data in Firestore
                                    FirebaseFirestore db = FirebaseFirestore.getInstance();
                                    db.collection("users").document(user.getUid()) // Use the user's UID as the document ID
                                            .set(userData)
                                            .addOnSuccessListener(aVoid -> {
                                                Toast.makeText(createAccount.this, "Account Created Successfully", Toast.LENGTH_SHORT).show();
                                                // Navigate to the next activity or perform other actions
                                            })
                                            .addOnFailureListener(e -> {
                                                Toast.makeText(createAccount.this, "Failed to save user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                // Handle the error, e.g., delete the user if necessary
                                                user.delete(); // Important: Delete the user if data saving fails
                                            });
                                }else {
                                    String errorMessage = task.getException().getMessage();
                                    Toast.makeText(createAccount.this, "Account Creation Failed: " + errorMessage, Toast.LENGTH_SHORT).show();
                                }
                            }
                        });






//                Intent signUpIntent = new Intent(getApplicationContext(), login.class);
//                startActivity(signUpIntent);
            }
        });
    }
}