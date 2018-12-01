package com.chat.firebasechat;

import android.content.Intent;
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

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Messsage";
    private FirebaseAuth mAuth;
    private EditText email;
    private EditText password;
    private Button save;
    private android.widget.TextView signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activityu);
        this.signup = (TextView) findViewById(R.id.signup);
        this.save = (Button) findViewById(R.id.save);
        this.password = (EditText) findViewById(R.id.password);
        this.email = (EditText) findViewById(R.id.email);
        mAuth = FirebaseAuth.getInstance();


        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        int index1 = signup.getText().toString().indexOf("?");
        int index2 = signup.getText().toString().indexOf(".");

        signup.setMovementMethod(LinkMovementMethod.getInstance());
        Spannable spannable = (Spannable) signup.getText();
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                overridePendingTransition(0, 0);
            }
        };
        spannable.setSpan(clickableSpan, index1 + 1, index2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        signup.setTextColor(getResources().getColor(R.color.colorPrimary));
    }

    public void login() {

        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            UserDetails.UserID = task.getResult().getUser().getUid();
                            startActivity(new Intent(getApplicationContext(), UserListActivity.class));
                            finish();
                            overridePendingTransition(0, 0);
                        }

                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail:failed", task.getException());
                            Toast.makeText(LoginActivity.this, task.getException().toString(),
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }
}
