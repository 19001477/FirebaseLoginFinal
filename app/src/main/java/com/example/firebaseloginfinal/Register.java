package com.example.firebaseloginfinal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void register(View view) {
        EditText txtUsername2 = (EditText) findViewById(R.id.txtUsername2);
        EditText txtPassword2 = (EditText) findViewById(R.id.txtPassword2);
        EditText txtPassword3 = (EditText) findViewById(R.id.txtPassword3);

        String userName = txtUsername2.getText().toString();
        String password1 = txtPassword2.getText().toString();
        String password2 = txtPassword3.getText().toString();

        try {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            DatabaseReference userRef = rootRef.child("Users").child(userName);

            ValueEventListener eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (!dataSnapshot.exists()) {
                        //User does not exist
                        if (password1.equals(password2)) {
                            //Registers user
                            userRef.setValue(userName);
                            userRef.child("userName").setValue(userName);
                            userRef.child("password").setValue(password1);
                            Toast.makeText(Register.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                            goToLogin();
                        } else {
                            Toast.makeText(getApplicationContext(), "Passwords do not match", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "User already exists", Toast.LENGTH_SHORT).show();
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            };
            userRef.addListenerForSingleValueEvent(eventListener);
        }
        catch (Exception e)
        {
            Log.w("Firebase", "Failed to update value.", e);
        }
    }

    private void goToLogin() {
        Intent intent = new Intent (this, MainActivity.class);
        startActivity(intent);
    }
}