package com.example.whatsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.whatsapp.Adapters.ChatAdapter;
import com.example.whatsapp.Model.MessagesModel;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;

public class Chat_detail_activity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth auth;
    TextView name;
    ImageView profileimg,back_btn,sendMessage;
    EditText editMsg;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_detail);

        firebaseDatabase = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        name = findViewById(R.id.namechat);
        profileimg = findViewById(R.id.profile_imagechat);
        back_btn = findViewById(R.id.back_arrowchat);
        recyclerView = findViewById(R.id.chat_recyclerViewchat);
        sendMessage = findViewById(R.id.sendthemessagechat);
        editMsg = findViewById(R.id.send_messsagechat);


        final String senderId = auth.getUid();
        String receiveId = getIntent().getStringExtra("userId");
        String receiverUserName = getIntent().getStringExtra("userName");
        String receiverProfilePic = getIntent().getStringExtra("profilePic");

        name.setText(receiverUserName);
        Picasso.get().load(receiverProfilePic).placeholder(R.drawable.profile_img_whats).into(profileimg);

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(Chat_detail_activity.this, Home_Activity.class);
                startActivity(it);
            }
        });

        final ArrayList<MessagesModel> messagesModels = new ArrayList<>();
        final ChatAdapter chatAdapter = new ChatAdapter(messagesModels, this);

        recyclerView.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //after clicking send button
        final String senderRoom = senderId + receiveId;
        final String ReceiverRoom = receiveId + senderId;


        //fetching data from data base
        firebaseDatabase.getReference().child("chats")
                .child(senderRoom)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        messagesModels.clear();
                        for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                            MessagesModel model = dataSnapshot.getValue(MessagesModel.class);
                            messagesModels.add(model);
                        }
                        chatAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });



        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             String msg = editMsg.getText().toString();
             final MessagesModel model = new MessagesModel(senderId, msg);
             model.setTimestamp(new Date().getTime());
             editMsg.setText("");

             firebaseDatabase.getReference().child("chats")
                     .child(senderRoom)
                     .push()
                     .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                         @Override
                         public void onSuccess(Void unused) {
                            firebaseDatabase.getReference().child("chats")
                                    .child(ReceiverRoom)
                                    .push()
                                    .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {

                                        }
                                    });
                         }
                     });
            }
        });

    }
}