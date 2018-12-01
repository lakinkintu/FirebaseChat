package com.chat.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Map;

import com.chat.firebasechat.Constantvalue.ChatOnItemClickListener;
import com.chat.firebasechat.Constantvalue.GroupMember;
import com.chat.firebasechat.Constantvalue.UserDetails;
import com.chat.firebasechat.newdesign.ScrollingActivity;

public class UserList extends AppCompatActivity {


    UserListAdapter userListAdapter;
    ArrayList<UserDetailsData> userDetailsDatas = new ArrayList<>();

    DatabaseReference databaseReference, groupDatabase;
    FirebaseDatabase firebaseDatabase;
    private android.support.v7.widget.RecyclerView userList;
    LinearLayoutManager linearLayoutManager;
    UserDetailsData userDetailsData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        this.userList = (RecyclerView) findViewById(R.id.userList);

        linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        userList.setLayoutManager(linearLayoutManager);
        userList.setHasFixedSize(true);

        userListAdapter = new UserListAdapter(userDetailsDatas, new ChatOnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                Intent intent = new Intent(getApplicationContext(), ScrollingActivity.class);
                intent.putExtra("UserName", userDetailsDatas.get(position).getUserName());
                intent.putExtra("UID", userDetailsDatas.get(position).getUID());
                startActivity(intent);
            }
        });
        userList.setAdapter(userListAdapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                userDetailsData = dataSnapshot.getValue(UserDetailsData.class);
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
        groupdataBase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_friends) {
            startActivity(new Intent(getApplicationContext(), CreateGroupActivity.class));

        }

        return super.onOptionsItemSelected(item);
    }

    public void groupdataBase() {
        groupDatabase = FirebaseDatabase.getInstance().getReference("Group");
        groupDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String groupnName = dataSnapshot.getKey();
                if (groupnName != null) {
                    ArrayList<GroupMember> groupMembers = (ArrayList<GroupMember>) dataSnapshot.getValue();

                    for (int i = 0; i < groupMembers.size(); i++) {
                        Map<String, String> stringStringMap = (Map<String, String>) groupMembers.get(i);
                        String name = stringStringMap.get("userName").toString();
                        String uid = stringStringMap.get("uid").toString();
                        if (UserDetails.UserID.equals(uid)) {
                            userDetailsData = new UserDetailsData();
                            userDetailsData.setUID("Group");
                            userDetailsData.setUserName("G_" + groupnName);
                            userDetailsDatas.add(userDetailsData);

                        }

                    }
                    userListAdapter.notifyDataSetChanged();
                }
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
