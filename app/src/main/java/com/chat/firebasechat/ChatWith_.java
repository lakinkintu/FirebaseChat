package com.chat.firebasechat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import com.chat.firebasechat.Constantvalue.UserDetails;

public class ChatWith_ extends AppCompatActivity {

    private RecyclerView chatListData;

    DatabaseReference databaseReference1, databaseReference2, databaseReference;
    FirebaseDatabase firebaseDatabase;
    private android.widget.EditText message;
    private android.widget.Button send;
    LinearLayoutManager linearLayoutManager;
    ChatWith_Adapter chatWith_adapter;
    ArrayList<ChatMessage> chatMessages = new ArrayList<>();
    String userName, UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_with_);

        this.send = (Button) findViewById(R.id.send);
        this.message = (EditText) findViewById(R.id.message);
        this.chatListData = (RecyclerView) findViewById(R.id.chatListData);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        linearLayoutManager.setStackFromEnd(true);
        chatListData.setLayoutManager(linearLayoutManager);
        chatListData.setHasFixedSize(true);
        chatWith_adapter = new ChatWith_Adapter(chatMessages);
        chatListData.setAdapter(chatWith_adapter);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMessage();
            }
        });

        userName = getIntent().getStringExtra("UserName");
        UID = getIntent().getStringExtra("UID");
        if (UID.equals("Group")) {

            databaseReference = FirebaseDatabase.getInstance().getReference("Message").child(userName + "_" + userName);
            ;
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    chatMessages.add(chatMessage);
                    chatListData.scrollToPosition(chatWith_adapter.getItemCount() - 1);
                    chatWith_adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        } else {
            databaseReference = FirebaseDatabase.getInstance().getReference("Message").child(UserDetails.Username + "_" + UserDetails.Second_User);
            ;
            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    ChatMessage chatMessage = dataSnapshot.getValue(ChatMessage.class);
                    chatMessages.add(chatMessage);
                    chatListData.scrollToPosition(chatWith_adapter.getItemCount() - 1);
                    chatWith_adapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

    public void sendMessage() {

        if (!UID.equals("Group")) {
            HashMap<String, String> user1 = new HashMap<>();
            user1.put("UserName", UserDetails.Username);
            user1.put("Chat", message.getText().toString());

            HashMap<String, String> user2 = new HashMap<>();
            user2.put("UserName", UserDetails.Username);
            user2.put("Chat", message.getText().toString());

            firebaseDatabase = FirebaseDatabase.getInstance();
            databaseReference1 = firebaseDatabase.getReference().child("Message").child(UserDetails.Username + "_" + UserDetails.Second_User);
            databaseReference2 = firebaseDatabase.getReference().child("Message").child(UserDetails.Second_User + "_" + UserDetails.Username);

            databaseReference1.push().setValue(user1);
            databaseReference2.push().setValue(user2);
            message.setText("");
        } else {
            groupMessage();
        }
    }

    public void groupMessage() {
        HashMap<String, String> user1 = new HashMap<>();
        user1.put("UserName", UserDetails.Username);
        user1.put("Chat", message.getText().toString());

        HashMap<String, String> user2 = new HashMap<>();
        user2.put("UserName", UserDetails.Username);
        user2.put("Chat", message.getText().toString());

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference1 = firebaseDatabase.getReference().child("Message").child(userName + "_" + userName);
        // databaseReference2 = firebaseDatabase.getReference().child("Message").child(userName + "_" + userName);

        databaseReference1.push().setValue(user1);
        //databaseReference2.push().setValue(user2);
        message.setText("");
    }
}
