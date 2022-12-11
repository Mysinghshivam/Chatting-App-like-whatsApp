package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.text.Format;

public class forgetPassword extends AppCompatActivity {
    Toolbar toolbar;
    EditText email;
    Button btn_forget_pw;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        toolbar = findViewById(R.id.toolbar);
        email = findViewById(R.id.Forget_pw_ei);
        btn_forget_pw = findViewById(R.id.forget_pw_btn);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btn_forget_pw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ei = email.getText().toString();
                if(ei.isEmpty()){
                    Toast.makeText(forgetPassword.this, "Please provide your email!!", Toast.LENGTH_SHORT).show();
                }else{
                    forgetPassword(ei);
                }
            }
        });
    }
    public void forgetPassword(String ei){
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.sendPasswordResetEmail(ei).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
             if (task.isSuccessful()){
                 Toast.makeText(forgetPassword.this, "Check your Email", Toast.LENGTH_SHORT).show();
                 Intent it = new Intent(forgetPassword.this, SigninActivity.class);
                 startActivity(it);
                 finish();
             }else{
                 Toast.makeText(forgetPassword.this,"Error!!"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
             }
            }
        });
    }


    //used for back button on title bar..
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            //super.onBackPressed();
            Intent it = new Intent(forgetPassword.this, SigninActivity.class);
            startActivity(it);
            finish();
        }
        return true;
    }
}