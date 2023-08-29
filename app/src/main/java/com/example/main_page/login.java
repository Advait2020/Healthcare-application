package com.example.main_page;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {

    private EditText emailEditText;
    private EditText passwordEditText;
    private FirebaseAuth authProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getSupportActionBar().setTitle("Login");

        emailEditText=findViewById(R.id.usernameLogin);
        passwordEditText=findViewById(R.id.passwordLogin);


        authProfile=FirebaseAuth.getInstance();

        //Login
        Button btnLogin =findViewById(R.id.loginButton);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String Email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (TextUtils.isEmpty(Email)) {
                    Toast.makeText(login.this, "Please enter your email", Toast.LENGTH_SHORT).show();
                    emailEditText.setError("Email is required");
                    emailEditText.requestFocus();
                } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
                    Toast.makeText(login.this, "please re-enter your email", Toast.LENGTH_SHORT).show();
                    emailEditText.setError("valid email is required");
                    emailEditText.requestFocus();
                } else if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login.this,
                            "Please enter your email", Toast.LENGTH_SHORT).show();
                    emailEditText.setError("Password is required");
                    emailEditText.requestFocus();
                } else {

                    loginUser(Email, password);
                }
            }
        });
    }

    private void loginUser(String email, String password) {

        authProfile.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    Toast.makeText(login.this,"You are logged in now",Toast.LENGTH_SHORT).show();
                    Intent loginUser=new Intent(login.this,MainActivity.class);
                    startActivity(loginUser);
                }else {

                    Toast.makeText(login.this,"Something went wrong",Toast.LENGTH_SHORT).show();
                }


            }
        });
    }


}
