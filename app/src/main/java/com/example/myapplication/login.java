package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login<mAuth> extends AppCompatActivity {
    TextInputLayout loginUsername, loginPassword;
    Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);

        //Hooks to all xml elements in activity_login.xml
        loginUsername = findViewById(R.id.username);
        loginPassword = findViewById(R.id.password);
        loginBtn = findViewById(R.id.signinbtn);
    }

    private Boolean validateUsername() {
        String val = loginUsername.getEditText().getText().toString();

        if (val.isEmpty()) {
            loginUsername.setError("Field cannot be empty");
            return false;
        } else {
            loginUsername.setError(null);
            loginUsername.setErrorEnabled(false);
            return true;
        }
    }

    private Boolean validatePassword() {
        String val = loginPassword.getEditText().getText().toString();

        if (val.isEmpty()) {
            loginPassword.setError("Field cannot be empty");
            return false;
        } else {
            loginPassword.setError(null);
            loginPassword.setErrorEnabled(false);
            return true;
        }
    }

    public void loginUser(View view){
        //Validate Login Info.
        if (!validateUsername() | !validatePassword())
        {
            return;
        }
        else
        {
            isUser();
        }
    }

    private void isUser() {
        String userEnteredUsername = loginUsername.getEditText().getText().toString().trim();
        String userEnteredPassword = loginPassword.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReferenceFromUrl("https://doctor-77acc-default-rtdb.firebaseio.com/");

        reference.child("doctors").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(userEnteredUsername)) {
                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);
                    if (passwordFromDB.equals(userEnteredPassword)) {
                        Toast.makeText(login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                        Intent i= new Intent(login.this, MainActivity.class);
                        String idFromDB = snapshot.child(userEnteredUsername).child("id").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);
                        i.putExtra("email", emailFromDB);
                        i.putExtra("id",idFromDB);
                        startActivity(i);
                        finish();
                    } else {
                        loginPassword.setError("Wrong Password.");
                        loginPassword.requestFocus();
                    }
                }
                    else{
                        loginUsername.setError("Username is Incorrect");
                        loginUsername.requestFocus();
                    }
                }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

        /*Query checkUser = reference.orderByChild("username").equalTo(userEnteredUsername);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String usernameFromDB = snapshot.child(userEnteredUsername).child("username").getValue(String.class);

                if(usernameFromDB.equals(userEnteredUsername)){

                    loginUsername.setError(null);
                    loginUsername.setErrorEnabled(false);

                    String passwordFromDB = snapshot.child(userEnteredUsername).child("password").getValue(String.class);

                    if (passwordFromDB.equals(userEnteredPassword)){

                        Intent i = new Intent(getApplicationContext(),register.class);
                        startActivity(i);
                        loginUsername.setError(null);
                        loginUsername.setErrorEnabled(false);

                        /*String nameFromDB = snapshot.child(userEnteredUsername).child("name").getValue(String.class);
                        String usernameFromDB = snapshot.child(userEnteredUsername).child("username").getValue(String.class);
                        String phonenumberFromDB = snapshot.child(userEnteredUsername).child("phone").getValue(String.class);
                        String emailFromDB = snapshot.child(userEnteredUsername).child("email").getValue(String.class);

                        Intent i = new Intent(getApplicationContext(),register.class);

                        //i.putExtra("name", nameFromDB);
                        //i.putExtra("username", usernameFromDB);
                        //i.putExtra("phone", phonenumberFromDB);
                        i.putExtra("email", emailFromDB);
                        //i.putExtra("password", passwordFromDB);

                        startActivity(i);
                        finish();
                    }
                    else {
                        loginPassword.setError("Wrong Password.");
                        loginPassword.requestFocus();
                    }
                }
                else {
                    loginUsername.setError("No such User exist");
                    loginUsername.requestFocus();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        }); */

    public void forgotpwd(View view){
        Intent i = new Intent(login.this, forgotpassword.class);
        startActivity(i);
    }
}