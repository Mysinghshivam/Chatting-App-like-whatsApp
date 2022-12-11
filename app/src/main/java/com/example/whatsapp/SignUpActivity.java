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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private TextView userName,Email,Password;
    private Button btnSignup;
    private FirebaseAuth Auth;
    ProgressDialog progressDialog;
    //for database we have to use this things
    private FirebaseDatabase database;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);



        userName = findViewById(R.id.username_signup);
        Email = findViewById(R.id.etEmailsignup);
        Password = findViewById(R.id.etPassword_signup);
        btnSignup = findViewById(R.id.btn_signup);


        Auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle("Creating Account");
        progressDialog.setMessage("We are creating your account");



        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un =userName.getText().toString();
                String ei =Email.getText().toString();
                String pw =Password.getText().toString();

                if(TextUtils.isEmpty(un) || TextUtils.isEmpty(ei) || TextUtils.isEmpty(pw)){
                    Toast.makeText(SignUpActivity.this, "Enter All Details Properly!!", Toast.LENGTH_SHORT).show();
                }else{
                    registration(un, ei, pw);
                }
            }
        });
    }

    public void registration(String un, String ei, String pw){
        //show progressDianlogbox
        progressDialog.show();

      Auth.createUserWithEmailAndPassword(ei,pw).addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
          @Override
          public void onComplete(@NonNull Task<AuthResult> task) {
              //close progressdialogbox
              progressDialog.dismiss();
             if (task.isSuccessful()){
                 //push valus in class
                 Users users = new Users(un, ei, pw);

                 String id = task.getResult().getUser().getUid();
                 database.getReference().child("Users").child(id).setValue(users);

                 Toast.makeText(SignUpActivity.this, "SUCCESSFULLY REGISTERED", Toast.LENGTH_SHORT).show();
                 Intent it = new Intent(SignUpActivity.this, SigninActivity.class);
                 startActivity(it);
                 finish();
             }else{
                 Toast.makeText(SignUpActivity.this, "REGISTRATION FAILED!!!", Toast.LENGTH_SHORT).show();
             }
          }
      });
    }

    public void alreadyhaveanaccount(View view) {
        Intent it = new Intent(SignUpActivity.this, SigninActivity.class);
        startActivity(it);
        finish();
    }
}