package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.Model.Users;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SigninActivity extends AppCompatActivity {

    private TextView Email,Password;
    private Button btnSignin,btn_google;
    ProgressDialog progressDialog;
    private FirebaseAuth Auth;
    private FirebaseDatabase database;
    GoogleSignInClient  mGoogleSignInClient;


    @Override
    protected void onStart() {
        super.onStart();
        if(Auth.getCurrentUser() != null){
            Intent it = new Intent(SigninActivity.this, Home_Activity.class);
            startActivity(it);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);


        Email = findViewById(R.id.etEmail_signin);
        Password = findViewById(R.id.etPassword_signin);
        btnSignin = findViewById(R.id.btn_signin);
        btn_google = findViewById(R.id.btn_google_signin);


        Auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SigninActivity.this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Login to your account");



        btnSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ei = Email.getText().toString();
                String  pw = Password.getText().toString();

                if(TextUtils.isEmpty(ei) || TextUtils.isEmpty(pw)){
                    Toast.makeText(SigninActivity.this, "Enter All Details Properly!!", Toast.LENGTH_SHORT).show();
                }else{
                    login(ei, pw);
                }
            }
        });
    }

    public void login(String ei, String pw){

        progressDialog.show();

        Auth.signInWithEmailAndPassword(ei, pw).addOnCompleteListener(SigninActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressDialog.dismiss();

                if (task.isSuccessful()){
                    String id = task.getResult().getUser().getUid();
                    database.getReference().child("Users").child(id).child("password").setValue(pw);


                    Toast.makeText(SigninActivity.this, "LOGIN SUCCESSFULL!!", Toast.LENGTH_SHORT).show();
                    Intent it = new Intent(SigninActivity.this, Home_Activity.class);
                    startActivity(it);
                    finish();
                }else{
                    Toast.makeText(SigninActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    public void clickforsignup(View view) {
        Intent i = new Intent(SigninActivity.this, SignUpActivity.class);
        startActivity(i);
        finish();
    }

    public void forgetPassword(View view) {
     Intent it = new Intent(SigninActivity.this, forgetPassword.class);
     startActivity(it);
     finish();
    }
}