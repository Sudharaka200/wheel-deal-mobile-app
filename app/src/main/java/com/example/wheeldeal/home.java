package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

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

        //buttonCategory
        ImageView imgCategory = findViewById(R.id.btnCategory);

        imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(getApplicationContext(), category.class);
                startActivity(categoryIntent);
            }
        });

        //buttonHome
//        ImageView imgHomeButton = findViewById(R.id.imgHome);
//
//        imgHomeButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent homeButtonIntent = new Intent(getApplicationContext(), home.class);
//                startActivity(homeButtonIntent);
//            }
//        });

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
        ImageView imgProfileButton = findViewById(R.id.imgProfile);

        imgProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profileButtonIntent = new Intent(getApplicationContext(), profile.class);
                startActivity(profileButtonIntent);
            }
        });












    }
}