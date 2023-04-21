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

public class CustLogin extends AppCompatActivity implements View.OnClickListener {

    private TextView tv_forgotpass, tv_register_cust;
    private EditText et_email, et_password;
    private Button btn_login;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_login);

        tv_forgotpass = findViewById(R.id.tv_forgotpassword);
        tv_register_cust = findViewById(R.id.tv_register_cust);
        et_email = findViewById(R.id.et_email);
        et_password = findViewById(R.id.et_custpassword);
        btn_login = findViewById(R.id.btn_custlogin);

        tv_forgotpass.setOnClickListener(this);
        tv_register_cust.setOnClickListener(this);
        btn_login.setOnClickListener(this);

        mAuth=FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
           case R.id.tv_forgotpassword:
                startActivity(new Intent(this,ForgotPassword.class));
                break;

            case R.id.btn_custlogin:
                userLogin();
                break;

            case R.id.tv_register_cust:
                startActivity(new Intent(this,CustRegister.class));
                break;
        }

    }
    private void userLogin() {

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
                        startActivity(new Intent(CustLogin.this, CustHomePage.class));//intent to homepage
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(CustLogin.this, "Please check your email to verify your account", Toast.LENGTH_LONG).show();

                    }

                } else{
                    Toast.makeText(CustLogin.this, "Failed to login! Make sure you login with the correct email and password", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}