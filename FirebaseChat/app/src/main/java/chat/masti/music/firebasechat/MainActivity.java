package chat.masti.music.firebasechat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import chat.masti.music.firebasechat.Constantvalue.UserDetails;

public class MainActivity extends AppCompatActivity {

    private android.widget.EditText email;
    private android.widget.EditText password;
    private android.widget.Button save;
    public static final String TAG = "Message";
    //
    private FirebaseAuth mAuth;
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;
    private EditText userName;
    private Button Login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        this.Login = (Button) findViewById(R.id.Login);
        this.userName = (EditText) findViewById(R.id.userName);
        this.save = (Button) findViewById(R.id.save);
        this.password = (EditText) findViewById(R.id.password);
        this.email = (EditText) findViewById(R.id.email);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                signup();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
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
                            ///startActivity(new Intent(MainActivity.this, LoginActivity.class));

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
