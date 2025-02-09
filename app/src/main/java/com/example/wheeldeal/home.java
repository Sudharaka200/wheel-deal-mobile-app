package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class home extends AppCompatActivity {

    ListView addListView;

    String addTitle[] = {"test1","test3","test4","test1","test2","test3","test4","test1","test2","test3"};

    int addImage[] = {
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            R.drawable.caricon,
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        addListView = findViewById(R.id.addList);

        ArrayList<HashMap<String, Object>> list = new ArrayList<>();

        for (int i = 0; i < addTitle.length; i++){
            HashMap<String, Object> map = new HashMap<>();
            map.put("addTitle", addTitle[i]);
            map.put("addImage",addImage[i]);
            list.add(map);
        }
        String[] from = {"addTitle","addImage"};

        int to[] = {R.id.addTitle, R.id.addImg};

        SimpleAdapter simpleAdapter = new SimpleAdapter(getApplicationContext(),list, R.layout.list_view_s, from, to);

        addListView.setAdapter(simpleAdapter);










        //addView
//        ImageView img = findViewById(R.id.imgPost);
//
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getApplicationContext(), addView.class);
//                startActivity(intent);
//            }
//        });

        //buttonCategory
        ImageView imgCategory = findViewById(R.id.btnCategory);

        imgCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent categoryIntent = new Intent(getApplicationContext(), category.class);
                startActivity(categoryIntent);
            }
        });

        //Add Post
        ImageView imgAddPost = findViewById(R.id.imgAddPost);

        imgAddPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                Intent intent;
                if (currentUser != null){
                    // User is logged in; go to the createnewadd page.
                    intent = new Intent(getApplicationContext(), createnewadd.class);
                }
                else {
                    // User is not logged in; redirect to the login page.
                    intent = new Intent(getApplicationContext(), activity_please_login_screen.class);
                }
                startActivity(intent);

//                Intent addPostIntent = new Intent(getApplicationContext(), createnewadd.class);
//                startActivity(addPostIntent);
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