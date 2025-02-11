package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class chatsNew extends AppCompatActivity {

    FirebaseAuth auth;
    TextView emailCheck;
    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chats_new);

        //user check
        auth = FirebaseAuth.getInstance();
//        emailCheck = findViewById(R.id.LoginCheckEmail);
        user = auth.getCurrentUser();
        if (user == null){
                Intent loginIntent = new Intent(getApplicationContext(), activity_please_login_screen.class);
                startActivity(loginIntent);
        }
        else {
//            emailCheck.setText(user.getEmail());
        }

        navigation();



    }

    private void navigation(){
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
//        ImageView imgChatbutton = findViewById(R.id.imgChats);
//
//        imgChatbutton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent chatButtonIntent = new Intent(getApplicationContext(), chatsNew.class);
//                startActivity(chatButtonIntent);
//            }
//        });

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