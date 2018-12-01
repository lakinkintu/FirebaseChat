package com.chat.firebasechat;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.chat.firebasechat.Constantvalue.UserDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    public static final String TAG = "Message";
    //
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private EditText email;
    private EditText password;
    private EditText userName;
    private Button save;
    private android.widget.TextView Login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        this.Login = (TextView) findViewById(R.id.Login);
        this.save = (Button) findViewById(R.id.save);
        this.userName = (EditText) findViewById(R.id.userName);
        this.password = (EditText) findViewById(R.id.password);
        this.email = (EditText) findViewById(R.id.email);

        int index1 = Login.getText().toString().indexOf("?");
        int index2 = Login.getText().toString().indexOf(".");

        Login.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spannable = (Spannable) Login.getText();
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                overridePendingTransition(0, 0);
            }
        };
        spannable.setSpan(clickableSpan, index1 + 1, index2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        Login.setTextColor(getResources().getColor(R.color.colorPrimary));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();
            }
        });


    }

    public void signup() {
        mAuth.createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {


                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            UserDetails.UserID = task.getResult().getUser().getUid();
                            setUserProfile(UserDetails.UserID);
                            email.setText("");
                            password.setText("");
                            userName.setText("");
                            startActivity(new Intent(MainActivity.this, UserListActivity.class));
                            finish();
                            overridePendingTransition(0, 0);
                        }
                        if (!task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    public void setUserProfile(String userID) {

        HashMap<String, String> stringStringHashMap = new HashMap<>();
        stringStringHashMap.put("UserName", userName.getText().toString());
        stringStringHashMap.put("UID", userID);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("User");
        databaseReference.child(UserDetails.UserID).setValue(stringStringHashMap);

    }
}
