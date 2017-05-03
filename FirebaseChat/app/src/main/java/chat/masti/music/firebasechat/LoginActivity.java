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

import chat.masti.music.firebasechat.Constantvalue.UserDetails;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Messsage";
    private android.widget.EditText email;
    private android.widget.EditText password;
    private android.widget.Button save;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_activityu);
        mAuth = FirebaseAuth.getInstance();
        this.save = (Button) findViewById(R.id.save);
        this.password = (EditText) findViewById(R.id.password);
        this.email = (EditText) findViewById(R.id.email);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    public void login() {

        mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());
                        if (task.isSuccessful()) {
                            UserDetails.UserID = task.getResult().getUser().getUid();
                            startActivity(new Intent(getApplicationContext(), UserList.class));
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
