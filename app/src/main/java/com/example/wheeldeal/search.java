package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_search);

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
//        ImageView imgSearchButton = findViewById(R.id.imgSearch);
//
//        imgSearchButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent searchButtonIntent = new Intent(getApplicationContext(), search.class);
//                startActivity(searchButtonIntent);
//            }
//        });

        //buttonChat
        ImageView imgChatbutton = findViewById(R.id.imgChats);
        imgChatbutton.setOnClickListener(v -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            Intent intent = currentUser != null ?
                    new Intent(getApplicationContext(), chatsNew.class) :
                    new Intent(getApplicationContext(), activity_please_login_screen.class);
            startActivity(intent);
        });

        //button profile
        ImageView imgProfileButton = findViewById(R.id.imgProfile);

        imgProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileButtonIntent = new Intent(getApplicationContext(), profile.class);
                startActivity(profileButtonIntent);
            }
        });


        //backButton
        ImageView backbuttonSeach = findViewById(R.id.btnBackSearch);

        backbuttonSeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backButtonSerach = new Intent(getApplicationContext(),home.class);
            }
        });
    }
}