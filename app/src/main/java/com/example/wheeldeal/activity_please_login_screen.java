package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class activity_please_login_screen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_please_login_screen);

        Button btnlogin = findViewById(R.id.btnloginpage);
        btnlogin.setOnClickListener(v -> {
            Intent i = new Intent(getApplicationContext(), login.class);
            startActivity(i);
        });

        // Initialize navigation buttons
        navigation();
    }

    private void navigation() {
        // Home Button
        ImageView imgHome = findViewById(R.id.imgHome);
        imgHome.setOnClickListener(v -> {
            Intent homeIntent = new Intent(getApplicationContext(), home.class);
            startActivity(homeIntent);
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

        // Search Button
        ImageView imgSearch = findViewById(R.id.imgSearch);
        imgSearch.setOnClickListener(v -> {
            Intent searchIntent = new Intent(getApplicationContext(), search.class);
            startActivity(searchIntent);
        });

        // Chat Button
        ImageView imgChats = findViewById(R.id.imgChats);
        imgChats.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = currentUser != null ?
                    new Intent(getApplicationContext(), chatsNew.class) :
                    new Intent(getApplicationContext(), activity_please_login_screen.class);
            startActivity(intent);
        });

        // Profile Button
        ImageView imgProfile = findViewById(R.id.imgProfile);
        imgProfile.setOnClickListener(v -> {
            Intent profileIntent = new Intent(getApplicationContext(), profile.class);
            startActivity(profileIntent);
        });
    }
}
