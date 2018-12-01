package com.chat.firebasechat;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.chat.firebasechat.Constantvalue.UserDetailsData;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

import com.chat.firebasechat.Constantvalue.CheckedUser;
import com.chat.firebasechat.Constantvalue.GroupMember;
import com.chat.firebasechat.Constantvalue.UserDetails;

public class CreateGroupActivity extends AppCompatActivity {

    CreateGroupAdapter userListAdapter;
    ArrayList<UserDetailsData> userDetailsDatas = new ArrayList<>();

    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private android.support.v7.widget.RecyclerView userList;
    LinearLayoutManager linearLayoutManager;
    private android.widget.EditText groupname;
    private android.widget.Button submit;
    HashMap<String, ArrayList<GroupMember>> user2 = new HashMap<>();
    ArrayList<GroupMember> username;
    ArrayList<GroupMember> userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_group);

        this.userList = (RecyclerView) findViewById(R.id.userList);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        userList.setLayoutManager(linearLayoutManager);
        userList.setHasFixedSize(true);


        userListAdapter = new CreateGroupAdapter(userDetailsDatas, getApplicationContext(), new CheckedUser() {
            @Override
            public void totalUser(ArrayList<UserDetailsData> selectedUser) {
                username = new ArrayList<>();
                userID = new ArrayList<>();


                userID.add(new GroupMember(UserDetails.Username, UserDetails.UserID));
                // username.add(UserDetails.Username);
                for (int i = 0; i < selectedUser.size(); i++) {
                    //username.add(selectedUser.get(i).getUserName());
                    //userID.add(selectedUser.get(i).getUID());
                    userID.add(new GroupMember(selectedUser.get(i).getUserName(), selectedUser.get(i).getUID()));
                }

            }
        });

        userList.setAdapter(userListAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                UserDetailsData userDetailsData = dataSnapshot.getValue(UserDetailsData.class);
                if (UserDetails.UserID.equals(userDetailsData.getUID())) {
                    UserDetails.Username = userDetailsData.getUserName();
                    getSupportActionBar().setTitle(userDetailsData.getUserName());
                } else {
                    userDetailsDatas.add(userDetailsData);

                }
                userListAdapter.notifyDataSetChanged();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_done, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_friends) {
            createGroup();
        }

        return super.onOptionsItemSelected(item);
    }

    public void createGroup() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.groupdialog);
        this.submit = (Button) dialog.findViewById(R.id.submit);
        this.groupname = (EditText) dialog.findViewById(R.id.groupname);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //user2.put("UserName", username);
                // user2.put("UID", userID);
                firebaseDatabase = FirebaseDatabase.getInstance();
                if (!groupname.getText().toString().isEmpty()) {
                    databaseReference = firebaseDatabase.getReference().child("Group").child(groupname.getText().toString());
                    databaseReference.setValue(userID);
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

}
