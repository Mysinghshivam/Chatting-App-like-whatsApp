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

import java.util.ArrayList;
import java.util.Date;

public class groupChatActivity extends AppCompatActivity {

    ImageView backbtn_group,sendBtn;
    RecyclerView recyclerView;
    TextView nameOfGroup;
    EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        backbtn_group = findViewById(R.id.back_arrowgroup);
        recyclerView = findViewById(R.id.chat_recyclerViewgroup);
        nameOfGroup = findViewById(R.id.namegroup);
        sendBtn = findViewById(R.id.sendthemessagegroup);
        editText = findViewById(R.id.send_messsagegroup);

        backbtn_group.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(groupChatActivity.this, Home_Activity.class);
                startActivity(it);
            }
        });

        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final ArrayList<MessagesModel> messagesModels = new ArrayList<>();

        final String senderId = FirebaseAuth.getInstance().getUid();
        nameOfGroup.setText("Friends group");

        final ChatAdapter adapter = new ChatAdapter(messagesModels, this);
        recyclerView.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //to show chats in recycler view
         database.getReference().child("Group Chat")
                         .addValueEventListener(new ValueEventListener() {
                             @Override
                             public void onDataChange(@NonNull DataSnapshot snapshot) {
                                 messagesModels.clear();
                                 for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                     MessagesModel model = dataSnapshot.getValue(MessagesModel.class);
                                     messagesModels.add(model);
                                 }
                                 adapter.notifyDataSetChanged();
                             }

                             @Override
                             public void onCancelled(@NonNull DatabaseError error) {

                             }
                         });



        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String message = editText.getText().toString();
                final MessagesModel model = new MessagesModel(senderId, message);
                model.setTimestamp(new Date().getTime());
                editText.setText("");

                database.getReference().child("Group Chat")
                        .push()
                        .setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });

            }
        });


    }
}