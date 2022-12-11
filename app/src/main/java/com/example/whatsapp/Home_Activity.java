package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.whatsapp.Adapters.viewPagerMessengerAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Home_Activity extends AppCompatActivity {


    Toolbar toolbar;
    FirebaseAuth  auth;
    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        auth = FirebaseAuth.getInstance();
        tabLayout = findViewById(R.id.tabLayout);
        viewPager = findViewById(R.id.viewPager);


        //step first for toolbar
        setSupportActionBar(toolbar);


        //step second if you want to make back button on toolbar
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //for set title of toolbar
        //getSupportActionBar().setTitle("My toolbar");
        //toolbar.setSubtitle("subtitle");

        viewPagerMessengerAdapter adapter = new viewPagerMessengerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.setting){
            //setting button clicked
            Intent it = new Intent(Home_Activity.this, SettingActivity.class);
            startActivity(it);
        }else if (itemId == R.id.logout){
            auth.signOut();
            Intent it = new Intent(Home_Activity.this, SigninActivity.class);
            startActivity(it);
            finish();
        }else if(itemId == R.id.group_chat){
          Intent it = new Intent(Home_Activity.this,groupChatActivity.class);
          startActivity(it);
        }

        return true;
    }
}