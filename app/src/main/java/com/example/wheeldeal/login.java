package com.example.wheeldeal;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
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

public class login extends AppCompatActivity {

    EditText email;
    EditText password;
    FirebaseAuth mAuth;

//    @Override
//    public void onStart(){
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null){
//            Intent signUpIntent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(signUpIntent);
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.txtEmail);
        password = findViewById(R.id.txtPassword);
        mAuth = FirebaseAuth.getInstance();

        Button buttonlog = findViewById(R.id.btnLogin);
        Button buttonWithoutLogin = findViewById(R.id.btnWithoutLogin);
        TextView signUp = findViewById(R.id.txtSignUp);

        buttonlog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String uemail, upassword;

                uemail = String.valueOf(email.getText());
                upassword = String.valueOf(password.getText());

                if (TextUtils.isEmpty(uemail)){
                    Toast.makeText(login.this, "Email is Empty !" , Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(upassword)){
                    Toast.makeText(login.this, "Password is Empty !" , Toast.LENGTH_SHORT).show();
                    return;
                }

                mAuth.signInWithEmailAndPassword(uemail, upassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(login.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                                    Intent log = new Intent(getApplicationContext(), category.class);
                                    startActivity(log);
                                    finish();
                                }else {
                                    Toast.makeText(login.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        buttonWithoutLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), category.class);
                startActivity(i);
            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signUpIntent = new Intent(getApplicationContext(), createAccount.class);
                startActivity(signUpIntent);
            }
        });
    }
}