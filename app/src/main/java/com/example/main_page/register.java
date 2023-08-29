package com.example.main_page;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.ktx.Firebase;

import java.nio.charset.StandardCharsets;


public class register extends AppCompatActivity {

    EditText editTextName,editTextEmail,editTextPassword,editTextPhoneNumber;
    Button btnRegister;
    FirebaseAuth mAuth;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
       mAuth= FirebaseAuth.getInstance();
        editTextName = findViewById(R.id.User_name);
        editTextEmail =findViewById(R.id.email);
        editTextPassword =findViewById(R.id.password);
        editTextPhoneNumber = findViewById(R.id.Phone_number);
        btnRegister = findViewById(R.id.registerButton);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, email, password, phoneNumber;
                name = String.valueOf(editTextName.getText());
                email = String.valueOf(editTextEmail.getText());
                password = String.valueOf(editTextPassword.getText());
                phoneNumber = String.valueOf(editTextPhoneNumber.getText());

                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(register.this, "Enter password", Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("Password is required");
                    editTextPassword.requestFocus();
                }
                else if (TextUtils.isEmpty(name)) {
                    Toast.makeText(register.this, "Enter name", Toast.LENGTH_SHORT).show();
                    editTextName.setError("Name is required");
                    editTextName.requestFocus();
                }
                else if (TextUtils.isEmpty(email)) {
                    Toast.makeText(register.this, "Enter Email", Toast.LENGTH_SHORT).show();
                    editTextEmail.setError("Email is required");
                    editTextEmail.requestFocus();
                }
                else if (TextUtils.isEmpty(phoneNumber)) {
                    Toast.makeText(register.this, "Enter phone number", Toast.LENGTH_SHORT).show();
                    editTextPhoneNumber.setError("Enter phone number");
                    editTextPhoneNumber.requestFocus();
                }
                else if(phoneNumber.length()!=10){
                    Toast.makeText(register.this,"Please re-enter 10 digit mobile number",Toast.LENGTH_LONG).show();
                    editTextPhoneNumber.setError("Mobile number should be 10 digits");
                    editTextPhoneNumber.requestFocus();
                }
                else{
                    registerUser(name,email,password,phoneNumber);
                }
            }
        });
    }
                private void registerUser(String name,String email,String password,String phoneNumber){

                    mAuth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        //add intent
                                        FirebaseUser firebaseUser = mAuth.getCurrentUser();

                                        //User display name of user
                                        UserProfileChangeRequest profileChangeRequest=new UserProfileChangeRequest.Builder().setDisplayName(name).build();
                                        firebaseUser.updateProfile(profileChangeRequest);

                                        //enter data into realtime database
                                        ReadWriteUserDetails writeUserDetails = new ReadWriteUserDetails(email,phoneNumber);

                                        //extracting user reference from database for registered user
                                        DatabaseReference referenceProfile = FirebaseDatabase.getInstance().getReference("Registered Users");

                                        referenceProfile.child(firebaseUser.getUid()).setValue(writeUserDetails).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {

                                                if(task.isSuccessful()) {

                                                    //send verification email
                                                    firebaseUser.sendEmailVerification();

                                                    Toast.makeText(register.this, "User registered successfully. Please verify your email",
                                                            Toast.LENGTH_SHORT).show();

                                                    //open main profile after successful  registration
                                                    Intent intent = new Intent(register.this, MainActivity.class);

                                                    //to prevent user from returning back to register activity on pressing back button after registration
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                    startActivity(intent);
                                                    finish();
                                                }else {

                                                    Toast.makeText(register.this, "Authentication failed.",
                                                            Toast.LENGTH_SHORT).show();

                                                }
                                            }
                                        });

                                    } else {

                                        // If sign in fails, display a message to the user.
                                        Toast.makeText(register.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
    }
}


