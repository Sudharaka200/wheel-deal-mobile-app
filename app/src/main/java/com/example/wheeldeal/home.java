package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class home extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        //addView
        ImageView img = findViewById(R.id.imgPost);

        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), addView.class);
                startActivity(intent);
            }
        });

        //homeButton
//        ImageView imageHome = findViewById(R.id.imgHome);
//
//        imageHome.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent homeInten = new Intent(getApplicationContext(), home.class);
//                startActivity(homeInten);
//            }
//        });

        //searchButton
        ImageView imageSearch = findViewById(R.id.imgSearch);

        imageSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent searchIntent = new Intent(getApplicationContext(), search.class);
                startActivity(searchIntent);
            }
        });

        //createNewAd
        ImageView imgAdPost = findViewById(R.id.imgAddPost);
        imgAdPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent adPostIntent = new Intent(getApplicationContext(), createnewadd.class);
                startActivity(adPostIntent);
            }
        });

        //chatbutton
        ImageView imgChatButton = findViewById(R.id.imgChat);
        imgChatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent chatIntent = new Intent(getApplicationContext(), chatsNew.class);
                startActivity(chatIntent);
            }
        });

        //profilebutton
        ImageView imgProfile = findViewById(R.id.imgProfile);

        imgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent(getApplicationContext(), profile.class);
                startActivity(profileIntent);
            }
        });

        ImageView imgCategory = findViewById(R.id.btnCategory);

        imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(getApplicationContext(), category.class);
                startActivity(categoryIntent);
            }
        });










    }
}