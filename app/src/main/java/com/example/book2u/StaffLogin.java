package com.example.book2u;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StaffLogin extends AppCompatActivity implements View.OnClickListener{

    private EditText et_email, et_password;
    private Button btn_login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_login);

        et_email = findViewById(R.id.et_staffid);
        et_password = findViewById(R.id.et_password);
        btn_login = findViewById(R.id.btn_stafflogin);

        btn_login.setOnClickListener(this);
        mAuth= FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_stafflogin:
                staffLogin();
                break;

        }
    }

    private void staffLogin() {

        String email=et_email.getText().toString().trim();
        String password=et_password.getText().toString().trim();

        if(email. isEmpty()){
            et_email.setError("Please fill the email");
            et_email.requestFocus();
            return;
        }

        if(password.isEmpty()){
            et_password.setError("Please enter the password");
            et_password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if (user.isEmailVerified()) {
                        startActivity(new Intent(StaffLogin.this, StaffHomepage.class));//intent to homepage
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(StaffLogin.this, "Please check your email to verify your account", Toast.LENGTH_LONG).show();

                    }

                } else{
                    Toast.makeText(StaffLogin.this, "Failed to login! Make sure you login with the correct email and password", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}