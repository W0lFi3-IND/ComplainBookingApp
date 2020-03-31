package com.wolfie.regcomplain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
TextView donthaveaccount;
EditText Email,Password;
ProgressBar mProgressBar;
    @Override
    protected void onStart() {

        super.onStart();

        mAuth.addAuthStateListener(mAuthListner);

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();

        //check the current user
        if (mAuth.getCurrentUser() != null) {
            startActivity(new Intent(MainActivity.this,complainscreen.class));
            finish();
        }
        setContentView(R.layout.activity_main);
        donthaveaccount = findViewById(R.id.signup);
        Email = findViewById(R.id.email);
        Password = findViewById(R.id.password);
        mProgressBar = findViewById(R.id.progressBar2);

        donthaveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });
        findViewById(R.id.LoginBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgressBar.setVisibility(View.VISIBLE);
                String email = Email.getText().toString();
                String password = Password.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                mProgressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d("sss", "signInWithEmail:success");
                                    startActivity(new Intent(getApplicationContext(),complainscreen.class));
                                    finish();
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w("u", "signInWithEmail:failure", task.getException());
                                    Toast.makeText(MainActivity.this, "Authentication failed.",
                                            Toast.LENGTH_SHORT).show();
                                }


                            }
                        });
            }
        });
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null) {
                    startActivity(new Intent(MainActivity.this,complainscreen.class));
                }

            }
        };

    }




    }



